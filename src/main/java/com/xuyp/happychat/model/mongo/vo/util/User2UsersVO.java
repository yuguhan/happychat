package com.xuyp.happychat.model.mongo.vo.util;

import com.xuyp.happychat.model.mongo.User;
import com.xuyp.happychat.model.mongo.vo.UsersVO;

import java.util.function.Function;

public class User2UsersVO {

    public static  UsersVO apply(User user) {
        return UsersVO.builder()
                .id(user.getId())
                .faceImage(user.getFaceImage())
                .faceImageBig(user.getFaceImageBig())
                .nickname(user.getNickname())
                .qrcode(user.getQrcode())
                .username(user.getUsername())
                .build();
    }

}
