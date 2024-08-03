package com.izhimu.seas.core.annotation;

import java.lang.annotation.*;

/**
 * 标记响应式返回值
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface React {
}
