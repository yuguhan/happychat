package com.xuyp.happychat.util.exception;

import com.xuyp.happychat.util.datatransfer.JSONResponse;
import com.xuyp.happychat.util.datatransfer.StatusInfo;
import com.xuyp.happychat.util.exception.util.DictConditionDis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;


/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 */
@Slf4j
@ControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    @Autowired
    DictConditionDis dictConditionDis;

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(HappyChatException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONResponse notFount(HappyChatException e) {


        log.error("业务异常------------------------------------");
        log.error(e.getMessage(), e);
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setStatus(e.getCode());
        jsonResponse.setMsg(e.getMessage());
        return jsonResponse;
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler({BindException.class, WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONResponse bindException(BindingResult e) {

        return getBindResult(e);
    }


    public JSONResponse getBindResult(BindingResult bindingResult) {

        List<ObjectError> errors = bindingResult.getAllErrors();
        StringBuilder sb = new StringBuilder();
        if (errors != null) {
            errors.forEach(objectError -> {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    String type = fieldError.getDefaultMessage();
                    if (type != null) {
                        String[] condition = type.split("#");
                        if (condition != null && condition.length > 1) {
                            sb.append(fieldError.getField()).append("=[").append(fieldError.getRejectedValue()).append("]->")
                                    .append(dictConditionDis.getConditionStr(condition[0], condition[1]));
                        }

                    }
                }
            });
        }

        String str = sb.toString();
        log.error("参数验证失败");
        log.error(str);

        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setStatus(400);
        jsonResponse.setMsg(str);
        return jsonResponse;
    }


    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONResponse notFount(RuntimeException e) {
        log.error("运行时异常-------------------------");
        log.error(e.getMessage(), e);
        JSONResponse jsonResponse = new JSONResponse();
        jsonResponse.setStatusInfo(StatusInfo.INTERNALSERVERERROR);
        return jsonResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JSONResponse error(MethodArgumentNotValidException e) {
        return getBindResult(e.getBindingResult());
    }


}
