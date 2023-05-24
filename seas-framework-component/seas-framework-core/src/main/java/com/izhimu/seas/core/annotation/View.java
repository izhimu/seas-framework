package com.izhimu.seas.core.annotation;

import java.lang.annotation.*;

/**
 * 视图忽略字段
 *
 * @author haoran
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface View {

    String value() default "";

    boolean ignore() default false;
}
