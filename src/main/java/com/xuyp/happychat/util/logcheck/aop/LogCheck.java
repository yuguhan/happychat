package com.xuyp.happychat.util.logcheck.aop;

import java.lang.annotation.*;

/**
 * @description: 字典查询列
 * @author: xuyp
 * @createTime: 2018/9/4
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface LogCheck {

    /**
     * 业务的名称,例如:"修改菜单"
     */
    String value() default "";
}
