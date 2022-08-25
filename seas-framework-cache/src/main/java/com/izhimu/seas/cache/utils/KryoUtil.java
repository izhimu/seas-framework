package com.izhimu.seas.cache.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Kryo序列化工具
 *
 * @author haoran
 * @version v1.0
 */
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return t;
    }
}
