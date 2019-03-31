package com.xuyp.happychat.util.logcheck.aop;




import com.xuyp.happychat.util.exception.HappyChatException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


import java.lang.reflect.Method;
import java.util.Arrays;


@Slf4j
@Aspect
@Component
public class LogAop {


    @Pointcut(value = "@annotation(com.xuyp.happychat.util.logcheck.aop.LogCheck)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
       //在业务逻辑之前，进行非空判断
        beforeHandle(point);
        //先执行业务
        Object result = point.proceed();
        System.out.println(result);

        return result;
    }

    private void beforeHandle(ProceedingJoinPoint point) {

        StringBuilder checkStr = new StringBuilder();
        try {
            //首先判断是否是方法上加的注解
            Signature sig = point.getSignature();
            MethodSignature msig = null;
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }

            //获得方法名称
            msig = (MethodSignature) sig;
            Object target = point.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

            LogCheck annotation = currentMethod.getAnnotation(LogCheck.class);

            String value = annotation.value();
            if (StringUtils.isNotBlank(value)) {
                log.info("当前正在执行的方法为：{}", value);
//                StringBuilder sb = new StringBuilder();
//
//                request.getParameterMap().forEach((k, v) -> sb.append(k + ":" + Arrays.toString(v) + "。"));
//                log.info("参数为：" + sb.toString());
//                log.info("登入的用户为:"+request.getHeader("appuserid"));
            }

        } catch (Exception e) {
            return;
        }

        if (!StringUtils.isEmpty(checkStr)) {
            throw new HappyChatException(checkStr.toString());
        }


    }



}