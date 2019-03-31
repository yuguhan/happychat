package com.xuyp.happychat.model.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("myFriend")
@Builder
public class MyFriend {
    @Id
    private String id;
    private String myUserId;
    private String myFriendUserId;

    public String getMyUserId() {
        return myUserId == null ? "" : myUserId;
    }

    public void setMyUserId(String myUserId) {
        this.myUserId = myUserId;
    }

    public String getMyFriendUserId() {
        return myFriendUserId == null ? "" : myFriendUserId;
    }

    public void setMyFriendUserId(String myFriendUserId) {
        this.myFriendUserId = myFriendUserId;
    }
}
