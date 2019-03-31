package com.xuyp.happychat.login.condition;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SetNickName {
    @NotBlank(message = "userId#notnull")
    private String userId;
    @NotBlank(message = "nickname#notnull")
    private String nickname;
}
