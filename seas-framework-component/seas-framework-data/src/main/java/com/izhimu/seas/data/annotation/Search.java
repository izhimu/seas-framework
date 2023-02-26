package com.izhimu.seas.data.annotation;

import com.izhimu.seas.data.enums.SearchType;

import java.lang.annotation.*;

/**
 * 搜索注解
 *
 * @author haoran
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Search {

    /**
     * 搜索类型
     *
     * @return SearchType
     */
    SearchType type() default SearchType.LIKE;

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
