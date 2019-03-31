package com.xuyp.happychat.friend.condition;

import lombok.Data;

@Data
public class OperFriendRequest {
    String sendUserId;
    String acceptUserId;
    int operType;
}
