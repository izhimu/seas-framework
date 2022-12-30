package com.izhimu.seas.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 默认排序字段
 *
 * @author haoran
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OrderBy {

    /**
     * 排序方式
     *
     * @return boolean
     */
    boolean asc() default true;
}
