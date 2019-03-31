
package com.xuyp.happychat.util.datatransfer;


import reactor.core.publisher.Mono;

public class JSONController {

    /**
     * 返回正确的结果
     * @param data
     * @return
     */
    protected static JSONResponse excute(Object data) {

        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setStatusInfo(StatusInfo.OK);
        jsonResponse.setData(data);
        return jsonResponse;
    }

    /**
     * 返回错误码
     * @param statusInfo
     * @return
     */
    protected JSONResponse failed(StatusInfo statusInfo) {
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setStatusInfo(statusInfo);
        return jsonResponse;
    }

    /**
     * 返回自定义错误码和提示信息 不建议使用
     * @param status
     * @param msg
     * @return
     */
    @Deprecated
    protected static JSONResponse failed(Integer status, String msg) {
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setMsg(msg);
        jsonResponse.setStatus(status);
        return jsonResponse;
    }

    /**
     * 返回自定义的错误提示信息，错误码固定 402
     * @param msg
     * @return
     */
    protected JSONResponse failed(String msg) {
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setMsg(msg);
        jsonResponse.setStatus(402);
        return jsonResponse;
    }

    /**
     * 返回正确信息
     * @return
     */
    protected JSONResponse succeed() {
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setStatusInfo(StatusInfo.OK);
        return jsonResponse;
    }

    /**
     *
     * @return
     */
    protected JSONResponse failed() {
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setStatusInfo(StatusInfo.INTERNALSERVERERROR);
        return jsonResponse;
    }
}
