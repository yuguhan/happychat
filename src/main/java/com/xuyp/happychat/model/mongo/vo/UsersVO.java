package com.xuyp.happychat.model.mongo.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UsersVO {
    private String id;
    private String username;
    private String faceImage;
    private String faceImageBig;
    private String nickname;
    private String qrcode;


}