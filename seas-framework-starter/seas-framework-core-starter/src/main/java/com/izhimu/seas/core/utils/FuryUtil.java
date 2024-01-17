package com.izhimu.seas.core.utils;

import cn.hutool.core.convert.Convert;
import io.fury.Fury;
import io.fury.config.Language;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;

/**
 * Fury序列化工具
 *
 * @author haoran
 */
@UtilityClass
public class FuryUtil {

    private static final Set<String> REGISTER_SET = new HashSet<>();

    private static final Fury fury = Fury.builder()
            .withLanguage(Language.XLANG)
            .withRefTracking(true)
            .build();

    public static <T> void register(Class<T> clazz) {
        String className = clazz.getCanonicalName();
        if (!REGISTER_SET.contains(className)) {
            fury.register(clazz, className);
            REGISTER_SET.add(className);
        }
    }

    /**
     * 序列化
     *
     * @param obj Object
     * @return byte[]
     */
    public static byte[] serialize(Object obj) {
        register(obj.getClass());
        return fury.serialize(obj);
    }

    /**
     * 反序列化
     *
     * @param bytes byte[]
     * @return Object
     */
    public static Object deserialize(byte[] bytes) {
        return fury.deserialize(bytes);
    }

    /**
     * 反序列化
     *
     * @param bytes byte[]
     * @return Object
     */
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        register(clazz);
        return Convert.convert(clazz, fury.deserialize(bytes));
    }
}
