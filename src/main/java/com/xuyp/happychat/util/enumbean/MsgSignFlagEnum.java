package com.xuyp.happychat.util.enumbean;

public enum MsgSignFlagEnum {

    NO(0, "未签收"),
    YES(1, "已签收");

    public final Integer type;
    public final String content;

    MsgSignFlagEnum(Integer type, String content) {
        this.type = type;
        this.content = content;
    }
}
