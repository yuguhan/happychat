package com.xuyp.happychat.util.enumbean;

public enum OperatorFriendRequestEnum {


    ACCEPT(1),
    IGNORE(0);

    private int status;

    OperatorFriendRequestEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}
