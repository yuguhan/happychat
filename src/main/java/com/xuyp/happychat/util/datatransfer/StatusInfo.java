package com.xuyp.happychat.util.datatransfer;

/**
 * 返回的状态码与原因
 * @author xiaoy
 */
public enum StatusInfo {

    OK(200, "执行成功！"),
    CREATED(201, "创建成功"),
    DELETE(204, "删除成功"),
    BADREQUEST(400, "请求的地址不存在或包含不支持的参数"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "被禁止访问"),
    NOTFOUND(404, "请求的资源不存在"),
    INTERNALSERVERERROR(500, "内部错误"),
    BUSINESSERROR(402, "业务异常");

    private Integer status;
    private String msg;

    private StatusInfo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

}
