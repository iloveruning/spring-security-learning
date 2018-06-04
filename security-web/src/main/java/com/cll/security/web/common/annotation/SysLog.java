package com.cll.security.web.common.annotation;

import java.lang.annotation.*;

/**
 * @author chenliangliang
 * @date 2018/6/4
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SysLog {

    String value() default "";


    /**
     *appId
     */
    String appid();

    /**
     * 业务id
     */
    String sevid();

    /**
     * 记录数据-JSON格式
     */
    String data();


    /**
     * 日志级别
     */
    String level() default "info";
}
