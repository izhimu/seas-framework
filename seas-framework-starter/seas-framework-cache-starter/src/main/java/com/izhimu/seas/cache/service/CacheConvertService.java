package com.izhimu.seas.cache.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;

/**
 * 缓存对象转换服务接口
 *
 * @author haoran
 */
public interface CacheConvertService {

    /**
     * 泛型对象转换
     *
     * @param obj   对象
     * @param clazz 类型
     * @param <T>   类型
     * @return 转换对象
     */
    default <T> T convert(Object obj, Class<T> clazz) {
        return Convert.convert(clazz, obj);
    }

    /**
     * 泛型对象转换
     *
     * @param obj           对象
     * @param typeReference 类型
     * @param <T>           类型
     * @return 转换对象
     */
    default <T> T convert(Object obj, TypeReference<T> typeReference) {
        return Convert.convert(typeReference, obj);
    }
}
