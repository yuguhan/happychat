package com.xuyp.happychat.util.exception;


import com.xuyp.happychat.util.datatransfer.StatusInfo;

import java.util.List;

public class HappyChatException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3662604392454244789L;

	private Integer status;

    private String message;

    /**
     * 接收错误码与信息
     * @param message
     */
    public HappyChatException(String message, Object ...objects) {
        this.status = 402;

        this.message = message;
    }

    /**
     *接收枚举类型
     * @param statusInfo
     */
    public HappyChatException(StatusInfo statusInfo) {
        this.status = statusInfo.getStatus();
        this.message = statusInfo.getMsg();
    }
    /**
     *接收枚举类型
     * @param statusInfo
     */
    /**
     * 1、替换占位符：{msg}
     * @param statusInfo
     * @param values
     */
    public HappyChatException(StatusInfo statusInfo, List<String> values) {
    	this.status = statusInfo.getStatus();
    	if(values==null||values.size()<=0) {return;}
    	StringBuilder sb=new StringBuilder();
    	for(String value:values) {
    		sb.append(","+value);
    	}
    	sb.deleteCharAt(0);
    	this.message = statusInfo.getMsg().replace("{params}", sb.toString());
    }
    public HappyChatException(StatusInfo statusInfo, String msg) {
    	this.status = statusInfo.getStatus();
    	this.message = statusInfo.getMsg().replace("{params}", msg);
    }

    public HappyChatException(Integer status, String msg) {
        this.status = status;
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return status;
    }

    public void setCode(Integer code) {
        this.status = code;
    }


}
