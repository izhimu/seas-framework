package com.izhimu.seas.cache.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.izhimu.seas.cache.service.CacheService;
import com.izhimu.seas.core.log.LogWrapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存实现
 *
 * @author haoran
 */
@Service
@ConditionalOnProperty(prefix = "seas.cache", name = "type", havingValue = "redis")
public class RedisCacheServiceImpl implements CacheService {

    private static final LogWrapper log = LogWrapper.build("RedisCache");

    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisCacheServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean hasKey(String key) {
        try {
            Optional<Boolean> optional = Optional.ofNullable(redisTemplate.hasKey(key));
            return optional.orElse(false);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean del(String... key) {
        try {
            if (key == null || key.length == 0) {
                return false;
            }
            Optional<Boolean> optional;
            if (key.length == 1) {
                optional = Optional.ofNullable(redisTemplate.delete(key[0]));
            } else {
                Optional<Long> optionalLong = Optional.ofNullable(redisTemplate.delete(Arrays.asList(key)));
                optional = Optional.of(optionalLong.orElse(-1L) > 0);
            }
            return optional.orElse(false);
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
            if (time <= 0 || timeUnit == null) {
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
            Optional<Boolean> optional = Optional.ofNullable(redisTemplate.expire(key, time, TimeUnit.SECONDS));
            return optional.orElse(false);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public boolean setExpire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || timeUnit == null) {
                return false;
            }
            Optional<Boolean> optional = Optional.ofNullable(redisTemplate.expire(key, time, timeUnit));
            return optional.orElse(false);
        } catch (Exception e) {
            log.error(e);
            return false;
        }
    }

    @Override
    public long getExpire(String key) {
        try {
            Optional<Long> optional = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS));
            return optional.orElse(-1L);
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
