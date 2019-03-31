package com.xuyp.happychat.login.service.impl;

import com.mongodb.client.result.UpdateResult;
import com.xuyp.happychat.login.condition.RegistOrLogin;
import com.xuyp.happychat.login.condition.SetNickName;
import com.xuyp.happychat.login.condition.UploadFaceBase64;
import com.xuyp.happychat.dao.UserRepository;
import com.xuyp.happychat.login.service.ILoginService;
import com.xuyp.happychat.model.mongo.User;
import com.xuyp.happychat.model.mongo.vo.UsersVO;
import com.xuyp.happychat.model.mongo.vo.util.User2UsersVO;
import com.xuyp.happychat.util.FastDFSClient;
import com.xuyp.happychat.util.FileUtils;
import com.xuyp.happychat.util.QRCodeUtils;
import com.xuyp.happychat.util.exception.HappyChatException;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class LoginService implements ILoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    QRCodeUtils qrCodeUtils;

    @Autowired
    FastDFSClient fastDFSClient;


    @Override
    public UsersVO registOrLogin(RegistOrLogin registOrLogin) throws IOException {
        String userName = registOrLogin.getUsername();
        String password = registOrLogin.getPassword();
        String prePath = "/qrcode/" + userName;
        String qrCodePath = prePath+ "/qrCode.png";
        File file = new File(prePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        qrCodeUtils.createQRCode(qrCodePath, "happy_chat:" + userName);

        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);

        if (userRepository.countByUsername(userName) > 0) {
            //登入
            if (userRepository.countByUsernameAndPassword(userName, password) > 0) {
                return userRepository.findByUsername(userName).map(User2UsersVO::apply).orElse(UsersVO.builder().build());
            }else {

                throw new HappyChatException("用户名、密码匹配失败");
            }
        }else {
            //注册
            User save = userRepository.save(User.builder()
                    .username(userName)
                    .password(password)
                    .cid(registOrLogin.getCid())
                    .qrcode(qrCodeUrl)
                    .build());
            return User2UsersVO.apply(save);
        }

    }

    @Override
    public User setNickName(SetNickName setNickName) {
        Optional<User> user = userRepository.findById(new ObjectId(setNickName.getUserId()));

        user.ifPresent(user1 -> {
            user1.setNickname(setNickName.getNickname());
            userRepository.save(user1);
        });
        return user.get();

    }

    @Override
    public User uploadFaceBase64(UploadFaceBase64 uploadFaceBase64) throws IOException {
        //获取base64字符串，然后转换为文件对象，再上传
        String base64Data = uploadFaceBase64.getFaceData();
        String userFacePath = "/temp/" + uploadFaceBase64.getUserId() + "userface64.png";
        try {
            FileUtils.base64ToFile(userFacePath, base64Data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传文件
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);
        String s  = fastDFSClient.uploadBase64(multipartFile);

        log.info(s);
        //获取缩略图
        String thump = "_80x80.";
        String arr[] = s.split("\\.");
        String thumpUrl = arr[0] + thump + arr[1];

        Optional<User> opUser = userRepository.findById(new ObjectId(uploadFaceBase64.getUserId()));
        opUser.ifPresent(user -> {
            user.setFaceImage(thumpUrl);
            user.setFaceImageBig(s);
            userRepository.save(user);

        });

        return opUser.get();
    }

}
