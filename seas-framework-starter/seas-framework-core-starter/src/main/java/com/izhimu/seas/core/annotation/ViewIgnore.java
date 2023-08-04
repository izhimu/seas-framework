package com.izhimu.seas.core.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
@JacksonAnnotationsInside
public @interface ViewIgnore {
}
