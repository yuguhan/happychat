package com.xuyp.happychat.netty.websocket;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * 用户id和channel关联处理
 */
public class UserChannelRel {

    private static HashMap<String, Channel> manageer = new HashMap<>();

    public static void put(String userId, Channel channel) {
        manageer.put(userId, channel);
    }

    public static Channel get(String userId) {
        return manageer.get(userId);
    }
}
