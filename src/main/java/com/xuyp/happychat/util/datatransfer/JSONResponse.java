
package com.xuyp.happychat.util.datatransfer;


import java.util.HashMap;

public class JSONResponse {
    private Integer status;
    private String msg;
    private Object data;
    private Long time;



    public Long getTime() {
        return System.currentTimeMillis();
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setStatusInfo(StatusInfo statusInfo) {
        this.status = statusInfo.getStatus();
        this.msg = statusInfo.getMsg();
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return this.data == null ? new HashMap<>() : this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JSONResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", time=" + time +
                '}';
    }
}
