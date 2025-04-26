package com.izhimu.seas.cache.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.izhimu.seas.cache.service.CacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * Redis缓存实现
 *
 * @author haoran
 */
@Service
@ConditionalOnProperty(prefix = "seas.cache", name = "type", havingValue = "redis")
public class RedisCacheServiceImpl implements CacheService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisCacheServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean del(String... key) {
        try {
            if (Objects.isNull(key) || key.length == 0) {
                return false;
            }
            boolean result;
            if (Objects.equals(1, key.length)) {
                result = redisTemplate.delete(key[0]);
            } else {
                result = redisTemplate.delete(Arrays.asList(key)) > 0;
            }
            return result;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            return convert(redisTemplate.opsForValue().get(key), clazz);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public <T> T get(String key, TypeReference<T> typeReference) {
        try {
            return convert(redisTemplate.opsForValue().get(key), typeReference);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long time) {
        try {
            set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || Objects.isNull(timeUnit)) {
                return false;
            }
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long time) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS));
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit));
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean setExpire(String key, long time) {
        try {
            if (time <= 0) {
                return false;
            }
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean setExpire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || Objects.isNull(timeUnit)) {
                return false;
            }
            return redisTemplate.expire(key, time, timeUnit);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public long getExpire(String key) {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e);
            return -1L;
        }
    }

    @Override
    public Set<String> keys(String pattern) {
        try {
            return Convert.convert(new TypeReference<>() {
            }, redisTemplate.keys(pattern));
        } catch (Exception e) {
            log.error(e);
            return Collections.emptySet();
        }
    }
}
