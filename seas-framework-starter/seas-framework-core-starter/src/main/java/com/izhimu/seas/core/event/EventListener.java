package com.izhimu.seas.core.event;

import java.lang.annotation.*;

/**
 * 事件监听器注解
 *
 * @author haoran
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventListener {

    /**
     * 监听的事件
     *
     * @return 事件
     */
    String[] value();

    /**
     * 自动注册
     *
     * @return true|false
     */
    boolean autoRegister() default true;

    /**
     * 排序
     *
     * @return int
     */
    int order() default 0;

    /**
     * 异步
     *
     * @return boolean
     */
    boolean async() default false;
}
