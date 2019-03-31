package com.xuyp.happychat.netty.websocket;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;



@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter{


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                //关闭无用channel，避免资源浪费
                channel.close();
            }

        }
        super.userEventTriggered(ctx, evt);
    }
}
