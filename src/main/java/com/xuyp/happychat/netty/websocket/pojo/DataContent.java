package com.xuyp.happychat.netty.websocket.pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class DataContent implements Serializable {

    private Integer action;
    private ChatMsg chatMsg;
    private String extand;

}
