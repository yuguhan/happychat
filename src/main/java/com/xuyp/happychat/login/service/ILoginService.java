package com.xuyp.happychat.login.service;

import com.xuyp.happychat.login.condition.RegistOrLogin;
import com.xuyp.happychat.login.condition.SetNickName;
import com.xuyp.happychat.login.condition.UploadFaceBase64;
import com.xuyp.happychat.model.mongo.User;
import com.xuyp.happychat.model.mongo.vo.UsersVO;

import java.io.IOException;

public interface ILoginService {

    /**
     * 进行登录验证
     * @param registOrLogin userId 用户id
     * @param registOrLogin password 密码
     * @return
     */
    UsersVO registOrLogin(RegistOrLogin registOrLogin) throws IOException;


    User setNickName(SetNickName setNickName);

    User uploadFaceBase64(UploadFaceBase64 uploadFaceBase64) throws IOException;
}
