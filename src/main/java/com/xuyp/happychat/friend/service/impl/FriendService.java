package com.xuyp.happychat.friend.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xuyp.happychat.dao.ChatMessageRepository;
import com.xuyp.happychat.dao.FriendRequestRepository;
import com.xuyp.happychat.friend.condition.*;
import com.xuyp.happychat.friend.service.IFriendService;
import com.xuyp.happychat.dao.MyFriendRepository;
import com.xuyp.happychat.dao.UserRepository;
import com.xuyp.happychat.model.mongo.ChatMessage;
import com.xuyp.happychat.model.mongo.FriendsRequest;
import com.xuyp.happychat.model.mongo.MyFriend;
import com.xuyp.happychat.model.mongo.User;
import com.xuyp.happychat.model.mongo.vo.FriendRequestVO;
import com.xuyp.happychat.model.mongo.vo.MyFriendsVO;
import com.xuyp.happychat.model.mongo.vo.UsersVO;
import com.xuyp.happychat.model.mongo.vo.util.User2FriendRequestVO;
import com.xuyp.happychat.model.mongo.vo.util.User2MyFriends;
import com.xuyp.happychat.model.mongo.vo.util.User2UsersVO;
import com.xuyp.happychat.netty.websocket.UserChannelRel;
import com.xuyp.happychat.netty.websocket.pojo.DataContent;
import com.xuyp.happychat.util.JsonUtils;
import com.xuyp.happychat.util.enumbean.MsgActionEnum;
import com.xuyp.happychat.util.enumbean.MsgSignFlagEnum;
import com.xuyp.happychat.util.enumbean.OperatorFriendRequestEnum;
import com.xuyp.happychat.util.exception.HappyChatException;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class FriendService implements IFriendService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    MyFriendRepository myFriendRepository;
    @Autowired
    FriendRequestRepository friendRequestRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ChatMessageRepository chatMessageRepository;


    @Override
    public UsersVO searchFriend(SearchFriend searchFriend) {

        Optional<User> optionalUser = userRepository.findByUsername(searchFriend.getFriendUsername());
        checkFriend(optionalUser, searchFriend.getMyUserId());
        return optionalUser.map(User2UsersVO::apply).get();
    }

    @Override
    public String addFriend(AddFriend addFriend) {
        Optional<User> optionalUser = userRepository.findByUsername(addFriend.getFriendUsername());
        checkFriend(optionalUser, addFriend.getMyUserId());
        //判断 请求表中是有已经存在此条记录
        Optional<FriendsRequest> optionalFriendsRequest
                = friendRequestRepository.findBySendUserIdAndAcceptUserId(addFriend.getMyUserId(), optionalUser.get().getId());
        if (!optionalFriendsRequest.isPresent()) {
            friendRequestRepository.save(FriendsRequest.builder()
                    .sendUserId(addFriend.getMyUserId())
                    .acceptUserId(optionalUser.get().getId())
                    .requestDateTime(System.currentTimeMillis())
                    .build());
        }

        return "ok";
    }

    @Override
    public List<FriendRequestVO> findFriendRequest(FindFriendRequest findFriendRequest) {

        Optional<List<FriendsRequest>> optionalFriendsRequests = friendRequestRepository.findByAcceptUserId(findFriendRequest.getUserId());
        if (optionalFriendsRequests.isPresent()) {
            List<String> userIds = optionalFriendsRequests.get().stream().map(friendsRequest -> friendsRequest.getSendUserId()).collect(Collectors.toList());
            Optional<List<User>> optionalUsers = Optional.ofNullable(mongoTemplate.find(Query.query(Criteria.where("_id").in(userIds)),
                    User.class));

            return optionalUsers.orElse(new ArrayList<>()).stream().map(User2FriendRequestVO::apply).collect(Collectors.toList());


        }

        return new ArrayList<>();
    }

    @Override
    public List<MyFriendsVO> operFriendRequest(OperFriendRequest operFriendRequest) {

        if (operFriendRequest.getOperType()==(OperatorFriendRequestEnum.ACCEPT.getStatus())) {
            //判断是有已经添加过好友了
            if (myFriendRepository.findByMyUserIdAndMyFriendUserId(operFriendRequest.getSendUserId(), operFriendRequest.getAcceptUserId()) != null) {
                myFriendRepository.save(MyFriend.builder().myUserId(operFriendRequest.getAcceptUserId()).myFriendUserId(operFriendRequest.getSendUserId()).build());
                myFriendRepository.save(MyFriend.builder().myFriendUserId(operFriendRequest.getAcceptUserId()).myUserId(operFriendRequest.getSendUserId()).build());
                friendRequestRepository.deleteBySendUserIdAndAcceptUserId(operFriendRequest.getSendUserId(), operFriendRequest.getAcceptUserId());

                Optional<Channel> optionalChannel = Optional.ofNullable(UserChannelRel.get(operFriendRequest.getSendUserId()));

                optionalChannel.ifPresent(channel->{
                    DataContent dataContent = new DataContent();
                    dataContent.setAction(MsgActionEnum.PULL_FRIEND.type);
                    channel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JsonUtils.objectToJson(dataContent)));
                });
            }
        }else{
            friendRequestRepository.deleteBySendUserIdAndAcceptUserId(operFriendRequest.getSendUserId(), operFriendRequest.getAcceptUserId());
        }

        return findFriends(new MyFriends(){{
            setUserId(operFriendRequest.getAcceptUserId());}});
    }

    @Override
    public User findUserInfo(FindUserInfo findUserInfo) {
        return userRepository.findByUsername(findUserInfo.getUserName()).orElse(null);
    }

    @Override
    public List<MyFriendsVO> findFriends(MyFriends myFriends) {

        Optional<List<MyFriend>> optionalMyFriends = myFriendRepository.findByMyUserId(myFriends.getUserId());
        if (optionalMyFriends.isPresent()) {
            List<String> userIds = optionalMyFriends.get().stream().map(myFriend -> myFriend.getMyFriendUserId()).collect(Collectors.toList());


            List<User> users = mongoTemplate.find(Query.query(Criteria.where("_id").in(userIds)),
                    User.class);

            return Optional.ofNullable(
                    mongoTemplate.find(Query.query(Criteria.where("_id").in(userIds)),
                            User.class).stream().map(User2MyFriends::apply).collect(Collectors.toList())).orElse(new ArrayList<>());
        }

        return new ArrayList<>();
    }

    @Override
    public String saveMsg(String msg, String receivedId, String senderId, int i) {
        ChatMessage save = chatMessageRepository.save(ChatMessage.builder()
                .acceptUserId(receivedId)
                .sendUserId(senderId)
                .msg(msg)
                .createTime(System.currentTimeMillis())
                .signFlag(i)
                .build());
        return save.getId();

    }

    @Override
    public void setSignFlag(List<String> ids) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("_id").in(ids)),
                Update.update("signFlag", MsgSignFlagEnum.YES.type),
                ChatMessage.class);

    }

    @Override
    public List<ChatMessage> getUnReadMsgList(String acceptUserId) {

        return chatMessageRepository.findAllByAcceptUserIdAndSignFlag(acceptUserId, MsgSignFlagEnum.NO.type);
    }

    private void checkFriend(Optional<User> optionalUser,String myUserId) {
        if (!optionalUser.isPresent()) {
            throw new HappyChatException("查无此人");
        }

        optionalUser.ifPresent(user->{
            if (myUserId.equals(user.getId())) {
                throw new HappyChatException("查找的是自己，不能添加");
            }
            Optional<MyFriend> optionalMyFriend = myFriendRepository.findByMyUserIdAndMyFriendUserId(myUserId, user.getId());
            optionalMyFriend.ifPresent(myFriend -> {
                throw new HappyChatException("查找的人已经和你是朋友了");
            });
        });


    }




}
