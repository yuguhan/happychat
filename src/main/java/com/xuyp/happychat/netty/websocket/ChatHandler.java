package com.xuyp.happychat.netty.websocket;

import com.xuyp.happychat.friend.service.IFriendService;
import com.xuyp.happychat.netty.websocket.pojo.ChatMsg;
import com.xuyp.happychat.netty.websocket.pojo.DataContent;
import com.xuyp.happychat.util.JsonUtils;
import com.xuyp.happychat.util.SpringUtil;
import com.xuyp.happychat.util.StringUtils;
import com.xuyp.happychat.util.enumbean.MsgActionEnum;
import com.xuyp.happychat.util.enumbean.MsgSignFlagEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame)
            throws Exception {
        String content = textWebSocketFrame.text();

        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        Integer action = dataContent.getAction();
        if (action == MsgActionEnum.CONNECT.type) {
            String senderId = dataContent.getChatMsg().getSenderId();
            Channel channel = channelHandlerContext.channel();
            UserChannelRel.put(senderId, channel);

        } else if (action == MsgActionEnum.CHAT.type) {
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msg = chatMsg.getMsg();
            String receivedId = chatMsg.getReceiverId();
            String senderId = chatMsg.getSenderId();
            IFriendService iFriendService = (IFriendService) SpringUtil.getBean("friendService");
            String msgId = iFriendService.saveMsg(msg, receivedId, senderId, MsgSignFlagEnum.NO.type);
            chatMsg.setMsgId(msgId);

            Optional<Channel> optionalChannel = Optional.ofNullable(UserChannelRel.get(receivedId));

            optionalChannel.ifPresent(channel -> {
                Optional<Channel> optionalFindChannel = Optional.ofNullable(users.find(channel.id()));
                optionalFindChannel.ifPresent(findChannel ->
                        channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(chatMsg))));
            });

            //todo 推送


        } else if (action == MsgActionEnum.SIGNED.type) {
            IFriendService iFriendService = (IFriendService) SpringUtil.getBean("friendService");
            String msgIdsStr = dataContent.getExtand();
            if (StringUtils.isNotBlank(msgIdsStr)) {
                String[] msgIds = msgIdsStr.split(",");
                List<String> ids = Arrays.asList(msgIds).stream().filter(msgId -> StringUtils.isNotBlank(msgId)).collect(Collectors.toList());
                iFriendService.setSignFlag(ids);
            }

        } else if (action == MsgActionEnum.KEEPALIVE.type) {

        }

        log.info("收取到客户端的消息：{}", content);

//        users.writeAndFlush(new TextWebSocketFrame("服务器接收到消息:" + LocalDateTime.now() + ",内容为：" + content));

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("连接成功");

        users.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常之后，关闭连接
        log.info("发生异常之后，关闭连接");
        ctx.channel().close();
        users.remove(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        users.remove(ctx.channel());
    }


}
