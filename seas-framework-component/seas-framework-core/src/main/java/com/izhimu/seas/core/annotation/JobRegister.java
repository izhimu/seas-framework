package com.izhimu.seas.core.annotation;

import com.izhimu.seas.core.enums.TimerType;

import java.lang.annotation.*;

/**
 * 任务自动注册
 *
 * @author haoran
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JobRegister {

    /**
     * 任务Key
     *
     * @return String
     */
    String key();

    /**
     * 任务名称
     *
     * @return String
     */
    String name();

    /**
     * 类型
     *
     * @return TimerType
     */
    TimerType type();

    /**
     * 表达式
     *
     * @return String
     */
    String expression();

    /**
     * 是否启用
     *
     * @return boolean
     */
    boolean enable() default true;
}
