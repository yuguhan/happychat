package com.xuyp.happychat.friend.service;

import com.xuyp.happychat.friend.condition.*;
import com.xuyp.happychat.model.mongo.ChatMessage;
import com.xuyp.happychat.model.mongo.User;
import com.xuyp.happychat.model.mongo.vo.FriendRequestVO;
import com.xuyp.happychat.model.mongo.vo.MyFriendsVO;
import com.xuyp.happychat.model.mongo.vo.UsersVO;

import java.util.List;

public interface IFriendService {
    UsersVO searchFriend(SearchFriend searchFriend);

    String  addFriend(AddFriend addFriend);

    List<FriendRequestVO> findFriendRequest(FindFriendRequest findFriendRequest);

    List<MyFriendsVO> operFriendRequest(OperFriendRequest operFriendRequest);

    User findUserInfo(FindUserInfo findUserInfo);

    List<MyFriendsVO> findFriends(MyFriends myFriends);

    String saveMsg(String msg, String receivedId, String senderId, int i);

    void setSignFlag(List<String> ids);

    List<ChatMessage> getUnReadMsgList(String acceptUserId);
}
