package com.izhimu.seas.core.annotation;

import java.lang.annotation.*;

/**
 * 操作日志记录注解
 *
 * @author haoran
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperationLog {

    /**
     * 名称
     *
     * @return String
     */
    String value();

    /**
     * 类型
     *
     * @return String
     */
    String type() default "";

    /**
     * 是否启用
     *
     * @return boolean
     */
    boolean enable() default true;
}
