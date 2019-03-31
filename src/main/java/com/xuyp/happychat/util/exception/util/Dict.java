package com.xuyp.happychat.util.exception.util;

public enum Dict {

    NOT_NULL("notnull", "%s不能为空！"),
    MAX("max","%s不能大于%s"),
    MIN("min","%s不能小于%s");
    private final String key;
    private final String msg;

    Dict(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }


    public String getInfo(String... objs) {
        return String.format(this.msg, objs);
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }
}
