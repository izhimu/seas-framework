package com.izhimu.seas.cache.serializer;

import com.izhimu.seas.core.utils.FuryUtil;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * Kryo Redis序列化工具
 *
 * @author haoran
 * @version v1.0
 */
public class FuryRedisSerializer implements RedisSerializer<Object> {

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        return FuryUtil.serialize(obj);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        return FuryUtil.deserialize(bytes);
    }
}
