package com.izhimu.seas.cache.service.impl;

import cn.hutool.core.lang.TypeReference;
import com.izhimu.seas.cache.service.CacheService;
import com.izhimu.seas.cache.service.SetCacheService;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 内存Set缓存
 *
 * @author haoran
 */
@Service
@ConditionalOnProperty(prefix = "seas.cache", name = "type", havingValue = "mem", matchIfMissing = true)
public class MemSetCacheServiceImpl implements SetCacheService {

    @Resource
    private CacheService cacheService;

    @Override
    public Set<Object> get(String key) {
        //noinspection Convert2Diamond
        Set<Object> objects = cacheService.get(key, new TypeReference<Set<Object>>() {
        });
        return Objects.nonNull(objects) ? objects : new HashSet<>();
    }

    @Override
    public <T> Set<T> get(String key, Class<T> clazz) {
        //noinspection Convert2Diamond
        Set<T> objects = cacheService.get(key, new TypeReference<Set<T>>() {
        });
        return Objects.nonNull(objects) ? objects : new HashSet<>();
    }

    @Override
    public boolean hasKey(String key, Object value) {
        return get(key).contains(value);
    }

    @Override
    public boolean set(String key, Object... values) {
        Set<Object> set = cacheService.hasKey(key) ? get(key) : new HashSet<>();
        Collections.addAll(set, values);
        return cacheService.set(key, set);
    }

    @Override
    public boolean setExpire(String key, long time, Object... values) {
        Set<Object> set = cacheService.hasKey(key) ? get(key) : new HashSet<>();
        Collections.addAll(set, values);
        return cacheService.set(key, set, time);
    }

    @Override
    public long size(String key) {
        return cacheService.hasKey(key) ? get(key).size() : 0;
    }

    @Override
    public boolean del(String key, Object... values) {
        if (!cacheService.hasKey(key)) {
            return false;
        }
        Set<Object> set = get(key);
        for (Object value : values) {
            set.remove(value);
        }
        if (set.isEmpty()) {
            return cacheService.del(key);
        } else {
            return cacheService.set(key, set);
        }
    }
}
