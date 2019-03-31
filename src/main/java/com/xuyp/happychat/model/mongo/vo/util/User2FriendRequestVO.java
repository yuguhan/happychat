package com.xuyp.happychat.model.mongo.vo.util;

import com.xuyp.happychat.model.mongo.User;
import com.xuyp.happychat.model.mongo.vo.FriendRequestVO;

public class User2FriendRequestVO {
    public static FriendRequestVO apply(User user) {
        return FriendRequestVO.builder()
                .sendFaceImage(user.getFaceImage())
                .sendNickname(user.getNickname())
                .sendUserId(user.getId())
                .sendUsername(user.getUsername())
                .build();
    }
}
