package com.xuyp.happychat.login.controller;

import com.xuyp.happychat.login.condition.RegistOrLogin;
import com.xuyp.happychat.login.condition.SetNickName;
import com.xuyp.happychat.login.condition.UploadFaceBase64;
import com.xuyp.happychat.login.service.ILoginService;
import com.xuyp.happychat.util.FastDFSClient;
import com.xuyp.happychat.util.FileUtils;
import com.xuyp.happychat.util.datatransfer.JSONController;
import com.xuyp.happychat.util.datatransfer.JSONResponse;
import com.xuyp.happychat.util.logcheck.aop.LogCheck;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/u")
public class LoginController extends JSONController {

    @Autowired
    ILoginService loginService;


    @PostMapping("/registOrLogin")
    @LogCheck("用户登录或注册")
    public Mono<JSONResponse> registOrLogin(@Validated @RequestBody RegistOrLogin registOrLogin) throws IOException {
        return Mono.justOrEmpty(excute(loginService.registOrLogin(registOrLogin)));
    }

    @PostMapping("/setNickname")
    @LogCheck("设置昵称")
    public Mono<JSONResponse> setNickName(@Validated @RequestBody SetNickName setNickName){

        return Mono.justOrEmpty(excute(loginService .setNickName(setNickName)));
    }

    @PostMapping("/uploadFaceBase64")
    @LogCheck("上传头像")
    public Mono<JSONResponse> uploadFaceBase64(@RequestBody UploadFaceBase64 uploadFaceBase64) throws Exception {


        return Mono.justOrEmpty(excute(loginService.uploadFaceBase64(uploadFaceBase64)));

    }


}
