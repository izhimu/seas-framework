package com.izhimu.seas.cache.service.impl;

import com.izhimu.seas.cache.service.ListCacheService;
import com.izhimu.seas.core.log.LogWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Redis List缓存实现
 *
 * @author haoran
 */
@Service
@ConditionalOnProperty(prefix = "seas.cache", name = "type", havingValue = "redis")
public class RedisListCacheServiceImpl implements ListCacheService {

    private static final LogWrapper log = LogWrapper.build("RedisListCache");

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisListCacheServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Object> get(String key) {
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Object> get(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error(e);
            return Collections.emptyList();
        }
    }

    @Override
    public Object get(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public <T> T get(String key, long index, Class<T> clazz) {
        try {
            return convert(redisTemplate.opsForList().index(key, index), clazz);
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    @Override
    public long size(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(0L);
        } catch (Exception e) {
            log.error(e);
            return 0;
        }
    }

    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long time) {
        try {
            if (time <= 0) {
                return false;
            }
            redisTemplate.opsForList().rightPush(key, value);
            return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || timeUnit == null) {
                return false;
            }
            redisTemplate.opsForList().rightPush(key, value);
            return Boolean.TRUE.equals(redisTemplate.expire(key, time, timeUnit));
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, List<Object> value, long time) {
        try {
            if (time <= 0) {
                return false;
            }
            redisTemplate.opsForList().rightPushAll(key, value);
            return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean set(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean del(String key, long count, Object value) {
        try {
            Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForList().remove(key, count, value));
            return optional.orElse(-1L) > 0;
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }
}
