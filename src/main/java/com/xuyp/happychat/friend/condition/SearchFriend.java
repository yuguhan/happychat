package com.xuyp.happychat.friend.condition;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchFriend {
    @NotBlank(message = "myUserId#notnull")
    private String myUserId;
    @NotBlank(message = "friendUsername#notnull")
    private String friendUsername;

}
