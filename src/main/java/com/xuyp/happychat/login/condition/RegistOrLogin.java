package com.xuyp.happychat.login.condition;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegistOrLogin {
    @NotBlank(message = "userName#notnull")
    private String username;
    @NotBlank(message = "password#notnull")
    private String password;
    @NotBlank(message = "cId#notnull")
    private String cid;

}
