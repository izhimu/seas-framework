package com.izhimu.seas.core.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Kryo序列化工具
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@UtilityClass
public class KryoUtil {

    private static final Pool<Kryo> KRYO_POOL = new Pool<>(true, false, 8) {
        @Override
        protected Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(false);
            return kryo;
        }
    };

    private static final byte[] EMPTY_BYTE = new byte[0];

    /**
     * 序列化
     *
     * @param obj Object
     * @return byte[]
     */
    public static byte[] serialize(Object obj) {
        Kryo kryo = KRYO_POOL.obtain();
        byte[] bytes = EMPTY_BYTE;
        try (
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Output output = new Output(byteArrayOutputStream)
        ) {
            kryo.writeClassAndObject(output, obj);
            output.flush();
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("", e);
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes byte[]
     * @return Object
     */
    public static Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        Kryo kryo = KRYO_POOL.obtain();
        Object t = null;
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                Input input = new Input(byteArrayInputStream)
        ) {
            t = kryo.readClassAndObject(input);
        } catch (Exception e) {
            log.error("", e);
        }
        return t;
    }
}
