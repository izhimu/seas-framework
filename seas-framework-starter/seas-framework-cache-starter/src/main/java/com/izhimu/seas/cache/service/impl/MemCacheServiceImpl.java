package com.izhimu.seas.cache.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.izhimu.seas.cache.entity.ExpireCache;
import com.izhimu.seas.cache.service.CacheService;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 内存缓存实现
 *
 * @author haoran
 */
@Service
@ConditionalOnProperty(prefix = "seas.cache", name = "type", havingValue = "mem", matchIfMissing = true)
public class MemCacheServiceImpl implements CacheService {

    private final Cache<String, ExpireCache> cache;

    public MemCacheServiceImpl() {
        this.cache = Caffeine.newBuilder()
                .initialCapacity(16)
                .expireAfter(new Expiry<String, ExpireCache>() {
                    @Override
                    public long expireAfterCreate(String key, ExpireCache value, long currentTime) {
                        return value.getTimeUnit().toNanos(value.getExpireTime());
                    }

                    @Override
                    public long expireAfterUpdate(String key, ExpireCache value, long currentTime, @NonNegative long currentDuration) {
                        return value.getTimeUnit().toNanos(value.getExpireTime());
                    }

                    @Override
                    public long expireAfterRead(String key, ExpireCache value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                })
                .build();
    }

    @Override
    public boolean hasKey(String key) {
        return Objects.nonNull(cache.getIfPresent(key));
    }

    @Override
    public boolean del(String... key) {
        cache.invalidateAll(ListUtil.toList(key));
        return true;
    }

    @Override
    public Object get(String key) {
        return ObjectUtil.defaultIfNull(cache.getIfPresent(key), ExpireCache::getValue, null);
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return ObjectUtil.defaultIfNull(cache.getIfPresent(key), data -> convert(data.getValue(), clazz), null);
    }

    @Override
    public <T> T get(String key, TypeReference<T> typeReference) {
        return ObjectUtil.defaultIfNull(cache.getIfPresent(key), data -> convert(data.getValue(), typeReference), null);
    }

    @Override
    public boolean set(String key, Object value) {
        cache.put(key, new ExpireCache(value));
        return true;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        cache.put(key, new ExpireCache(value, time));
        return true;
    }

    @Override
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        cache.put(key, new ExpireCache(value, time, timeUnit));
        return true;
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        if (!hasKey(key)) {
            set(key, value);
        }
        return true;
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long time) {
        if (!hasKey(key)) {
            set(key, value, time);
        }
        return true;
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
        if (!hasKey(key)) {
            set(key, value, time, timeUnit);
        }
        return true;
    }

    @Override
    public boolean setExpire(String key, long time) {
        ExpireCache data = cache.getIfPresent(key);
        if (Objects.isNull(data)) {
            return false;
        }
        data.setExpireTime(time);
        cache.put(key, data);
        return true;
    }

    @Override
    public boolean setExpire(String key, long time, TimeUnit timeUnit) {
        ExpireCache data = cache.getIfPresent(key);
        if (Objects.isNull(data)) {
            return false;
        }
        data.setExpireTime(time);
        data.setTimeUnit(timeUnit);
        cache.put(key, data);
        return true;
    }

    @Override
    public long getExpire(String key) {
        ExpireCache data = cache.getIfPresent(key);
        if (Objects.isNull(data)) {
            return -1;
        }
        return data.getExpireTime();
    }

    @Override
    public Set<String> keys(String pattern) {
        return cache.asMap().keySet();
    }
}
