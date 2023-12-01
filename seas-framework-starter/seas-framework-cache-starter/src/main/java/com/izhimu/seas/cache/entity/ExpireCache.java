package com.izhimu.seas.cache.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * 过期缓存
 *
 * @author haoran
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpireCache {

    /**
     * 值
     */
    private Object value;
    /**
     * 过期时间
     */
    private long expireTime = Long.MAX_VALUE;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public ExpireCache(Object value) {
        this.value = value;
    }

    public ExpireCache(Object value, long expireTime) {
        this.value = value;
        this.expireTime = expireTime;
    }
}
