package com.izhimu.seas.cache.connector;

import com.izhimu.seas.common.utils.KryoUtil;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Kryo编解码器
 *
 * @author haoran
 * @version 1.0
 */
public class KryoRedisCodec implements RedisCodec<String, Object> {

    private static final byte[] EMPTY = new byte[0];
    private final StringCodec STRING_CODEC;

    public KryoRedisCodec() {
        this.STRING_CODEC = new StringCodec(StandardCharsets.UTF_8);
    }

    @Override
    public String decodeKey(ByteBuffer bytes) {
        return STRING_CODEC.decodeKey(bytes);
    }

    @Override
    public Object decodeValue(ByteBuffer bytes) {
        return KryoUtil.deserialize(getBytes(bytes));
    }

    @Override
    public ByteBuffer encodeKey(String key) {
        return STRING_CODEC.encodeKey(key);
    }

    @Override
    public ByteBuffer encodeValue(Object value) {
        if (Objects.isNull(value)) {
            return ByteBuffer.wrap(EMPTY);
        }
        return ByteBuffer.wrap(KryoUtil.serialize(value));
    }

    private byte[] getBytes(ByteBuffer buffer) {

        int remaining = buffer.remaining();

        if (remaining == 0) {
            return EMPTY;
        }

        byte[] b = new byte[remaining];
        buffer.get(b);
        return b;
    }
}
