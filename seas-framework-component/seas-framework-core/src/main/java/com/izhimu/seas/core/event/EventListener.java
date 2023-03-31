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
     * 自动注册
     *
     * @return true|false
     */
    boolean autoRegister() default true;
}
