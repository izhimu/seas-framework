package com.izhimu.seas.cache.service.impl;

import cn.hutool.core.convert.Convert;
import com.izhimu.seas.cache.service.SetCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * Redis Set缓存实现
 *
 * @author haoran
 */
@Service
@ConditionalOnProperty(prefix = "seas.cache", name = "type", havingValue = "redis")
public class RedisSetCacheServiceImpl implements SetCacheService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisSetCacheServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Set<Object> get(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error(e);
            return Collections.emptySet();
        }
    }

    @Override
    public <T> Set<T> get(String key, Class<T> clazz) {
        try {
            return Convert.toSet(clazz, redisTemplate.opsForSet().members(key));
        } catch (Exception e) {
            log.error(e);
            return Collections.emptySet();
        }
    }

    @Override
    public boolean hasKey(String key, Object value) {
        try {
            Optional<Boolean> optional = Optional.ofNullable(redisTemplate.opsForSet().isMember(key, value));
            return optional.orElse(false);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object... values) {
        try {
            Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForSet().add(key, values));
            return optional.orElse(-1L) > 0;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean setExpire(String key, long time, Object... values) {
        try {
            if (time <= 0) {
                return false;
            }
            Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForSet().add(key, values));
            if (optional.isPresent()) {
                return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
            }
            return false;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public long size(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().size(key)).orElse(0L);
        } catch (Exception e) {
            log.error(e);
            return 0;
        }
    }

    @Override
    public boolean del(String key, Object... values) {
        try {
            Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForSet().remove(key, values));
            return optional.orElse(-1L) > 0;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }
}
