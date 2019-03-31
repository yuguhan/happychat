package com.xuyp.happychat.model.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("chatMessage")
@Builder
public class ChatMessage {
    @Id
    private String id;
    private String sendUserId;
    private String acceptUserId;
    private String msg;
    private int signFlag;
    private long createTime;

    public String getId() {
        return id;
    }

    public void setSignFlag(int signFlag) {
        this.signFlag = signFlag;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSignFlag() {
        return signFlag;
    }

    public long getCreateTime() {
        return createTime;
    }


    public String getSendUserId() {
        return sendUserId == null ? "" : sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getAcceptUserId() {
        return acceptUserId == null ? "" : acceptUserId;
    }

    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
