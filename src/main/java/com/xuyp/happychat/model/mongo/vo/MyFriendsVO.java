package com.xuyp.happychat.model.mongo.vo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyFriendsVO {
    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;

    
    
}