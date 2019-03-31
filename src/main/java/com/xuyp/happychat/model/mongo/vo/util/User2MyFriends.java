package com.xuyp.happychat.model.mongo.vo.util;

import com.xuyp.happychat.model.mongo.User;
import com.xuyp.happychat.model.mongo.vo.MyFriendsVO;

public class User2MyFriends {
    public static MyFriendsVO apply(User user) {
        return MyFriendsVO.builder()
                .friendFaceImage(user.getFaceImage())
                .friendNickname(user.getNickname())
                .friendUserId(user.getId())
                .friendUsername(user.getUsername())
                .build();

    }
}
