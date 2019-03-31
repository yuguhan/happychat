package com.xuyp.happychat.login.condition;

import lombok.Data;

@Data
public class UploadFaceBase64 {
    private String userId;
    private String faceData;
    private String nickname;
}
