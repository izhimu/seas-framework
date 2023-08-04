package com.izhimu.seas.cache.serializer;

import com.izhimu.seas.core.utils.KryoUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Kryo Redis序列化工具
 *
 * @author haoran
 * @version v1.0
 */
public class KryoRedisSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        return KryoUtil.serialize(obj);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return KryoUtil.deserialize(bytes);
    }
}
