package com.izhimu.seas.data.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 *
 * @author haoran
 * @version v1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Permission {

    /**
     * 组织控制字段
     *
     * @return String
     */
    String orgField() default "org_id";

    /**
     * 用户控制字段
     *
     * @return String
     */
    String userField() default "created_by";

    /**
     * 是否启用
     *
     * @return boolean
     */
    boolean enable() default true;
}
