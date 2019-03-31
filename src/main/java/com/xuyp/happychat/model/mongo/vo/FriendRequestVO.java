package com.xuyp.happychat.model.mongo.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: 好友请求发送方的信息
 */
@Data
@Builder
public class FriendRequestVO {
	
    private String sendUserId;
    private String sendUsername;
    private String sendFaceImage;
    private String sendNickname;
    

}