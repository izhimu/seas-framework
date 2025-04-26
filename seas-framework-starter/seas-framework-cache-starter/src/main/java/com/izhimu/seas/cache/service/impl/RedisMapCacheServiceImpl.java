package com.izhimu.seas.cache.service.impl;

import com.izhimu.seas.cache.service.MapCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * Redis Map缓存实现
 *
 * @author haoran
 */
@Service
@ConditionalOnProperty(prefix = "seas.cache", name = "type", havingValue = "redis")
public class RedisMapCacheServiceImpl implements MapCacheService {

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisMapCacheServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object get(String key, String item) {
        try {
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public <T> T get(String key, String item, Class<T> clazz) {
        try {
            return convert(redisTemplate.opsForHash().get(key, item), clazz);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public Map<Object, Object> getToMap(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.error(e);
            return Collections.emptyMap();
        }
    }

    @Override
    public List<Object> getValues(String key) {
        try {
            return redisTemplate.opsForHash().values(key);
        } catch (Exception e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean set(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Map<String, Object> map, long time) {
        try {
            if (time <= 0) {
                return false;
            }
            redisTemplate.opsForHash().putAll(key, map);
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Map<String, Object> map, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || Objects.isNull(timeUnit)) {
                return false;
            }
            redisTemplate.opsForHash().putAll(key, map);
            return redisTemplate.expire(key, time, timeUnit);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return redisTemplate.expire(key, time, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, String item, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || Objects.isNull(timeUnit)) {
                return false;
            }
            redisTemplate.opsForHash().put(key, item, value);
            return redisTemplate.expire(key, time, timeUnit);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean del(String key, Object... item) {
        try {
            return redisTemplate.opsForHash().delete(key, item) > 0;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean hasKey(String key, String item) {
        try {
            return redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }
}
