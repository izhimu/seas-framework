package com.izhimu.seas.cache.service;

import cn.hutool.core.convert.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存服务
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class RedisService {

    public static final String REDIS_SERVICE_SET = "RedisService.set:";
    public static final String REDIS_SERVICE_HASH_CACHE_SET = "RedisService.HashCache.set:";
    public static final String REDIS_SERVICE_LIST_CACHE_GET = "RedisService.ListCache.get:";
    public static final String REDIS_SERVICE_LIST_CACHE_SET = "RedisService.ListCache.set:";
    /**
     * Spring提供的RedisTemplate
     */
    private final RedisTemplate<Object, Object> redisTemplate;

    /**
     * hash缓存
     */
    private final HashCache hashCache;
    /**
     * set缓存
     */
    private final SetCache setCache;
    /**
     * list缓存
     */
    private final ListCache listCache;

    public RedisService(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashCache = new HashCache();
        this.setCache = new SetCache();
        this.listCache = new ListCache();
    }

    /**
     * 获取hash缓存
     *
     * @return hash缓存
     */
    public HashCache hashCache() {
        return this.hashCache;
    }

    /**
     * 获取set缓存
     *
     * @return set缓存
     */
    public SetCache setCache() {
        return this.setCache;
    }

    /**
     * 获取list缓存
     *
     * @return list缓存
     */
    public ListCache listCache() {
        return this.listCache;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return true成功|false失败
     */
    public boolean expire(String key, long time) {
        try {
            if (time <= 0) {
                return false;
            }
            Optional<Boolean> optional = Optional.ofNullable(redisTemplate.expire(key, time, TimeUnit.SECONDS));
            return optional.orElse(false);
        } catch (Exception e) {
            log.error("RedisService.expire:", e);
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key      键
     * @param time     时间(秒)
     * @param timeUnit 时间单位
     * @return true成功|false失败
     */
    public boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || timeUnit == null) {
                return false;
            }
            Optional<Boolean> optional = Optional.ofNullable(redisTemplate.expire(key, time, timeUnit));
            return optional.orElse(false);
        } catch (Exception e) {
            log.error("RedisService.expire:", e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键
     * @return 时间(秒) 0 永久有效|1 错误
     */
    public long getExpire(String key) {
        try {
            Optional<Long> optional = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS));
            return optional.orElse(-1L);
        } catch (Exception e) {
            log.error("RedisService.getExpire:", e);
            return -1L;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true存在|false不存在
     */
    public boolean hasKey(String key) {
        try {
            Optional<Boolean> optional = Optional.ofNullable(redisTemplate.hasKey(key));
            return optional.orElse(false);
        } catch (Exception e) {
            log.error("RedisService.hasKey:", e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
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
            log.error("RedisService.del:", e);
            return false;
        }
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("RedisService.get:", e);
            return null;
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key, Class<T> clazz) {
        try {
            return getObject(redisTemplate.opsForValue().get(key), clazz);
        } catch (Exception e) {
            log.error("RedisService.get:", e);
            return null;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功|false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(REDIS_SERVICE_SET, e);
            return false;
        }

    }

    /**
     * 失效时间缓存放入
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            set(key, value, time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error(REDIS_SERVICE_SET, e);
            return false;
        }
    }

    /**
     * 失效时间缓存放入
     *
     * @param key      键
     * @param value    值
     * @param time     时间
     * @param timeUnit 时间单位
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time <= 0 || timeUnit == null) {
                return false;
            }
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
            return true;
        } catch (Exception e) {
            log.error(REDIS_SERVICE_SET, e);
            return false;
        }
    }

    /**
     * 不存在时放入
     *
     * @param key   String
     * @param value Object
     * @return boolean
     */
    public boolean setIfAbsent(String key, Object value) {
        try {
            return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
        } catch (Exception e) {
            log.error(REDIS_SERVICE_SET, e);
            return false;
        }
    }

    /**
     * 泛型对象转换
     *
     * @param obj   对象
     * @param clazz 类型
     * @param <T>   类型
     * @return 转换对象
     */
    private <T> T getObject(Object obj, Class<T> clazz) {
        return Convert.convert(clazz, obj);
    }

    public class HashCache {

        /**
         * HashGet
         *
         * @param key  键
         * @param item 项
         * @return 值
         */
        public Object get(String key, String item) {
            try {
                return redisTemplate.opsForHash().get(key, item);
            } catch (Exception e) {
                log.error("RedisService.HashCache.get:", e);
                return null;
            }
        }

        /**
         * HashGet
         *
         * @param key  键
         * @param item 项
         * @return 值
         */
        public <T> T get(String key, String item, Class<T> clazz) {
            try {
                return getObject(redisTemplate.opsForHash().get(key, item), clazz);
            } catch (Exception e) {
                log.error("RedisService.HashCache.get:", e);
                return null;
            }
        }

        /**
         * 获取hashKey对应的所有键值
         *
         * @param key 键
         * @return 对应的多个键值
         */
        public Map<Object, Object> getValuesToMap(String key) {
            try {
                return redisTemplate.opsForHash().entries(key);
            } catch (Exception e) {
                log.error("RedisService.HashCache.getValues:", e);
                return Collections.emptyMap();
            }
        }

        /**
         * 获取hashKey对应的所有键值
         *
         * @param key 键
         * @return 对应的多个键值
         */
        public List<Object> getValues(String key) {
            try {
                return redisTemplate.opsForHash().values(key);
            } catch (Exception e) {
                log.error("RedisService.HashCache.getValues:", e);
                return Collections.emptyList();
            }
        }

        /**
         * HashSet
         *
         * @param key 键
         * @param map 对应多个键值
         * @return true 成功|false 失败
         */
        public boolean set(String key, Map<String, Object> map) {
            try {
                redisTemplate.opsForHash().putAll(key, map);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_HASH_CACHE_SET, e);
                return false;
            }
        }

        /**
         * HashSet 并设置时间
         *
         * @param key  键
         * @param map  对应多个键值
         * @param time 时间(秒)
         * @return true成功 false失败
         */
        public boolean set(String key, Map<String, Object> map, long time) {
            try {
                if (time <= 0) {
                    return false;
                }
                redisTemplate.opsForHash().putAll(key, map);
                expire(key, time);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_HASH_CACHE_SET, e);
                return false;
            }
        }

        /**
         * HashSet 并设置时间
         *
         * @param key      键
         * @param map      对应多个键值
         * @param time     时间
         * @param timeUnit 时间单位
         * @return true成功 false失败
         */
        public boolean set(String key, Map<String, Object> map, long time, TimeUnit timeUnit) {
            try {
                if (time <= 0 || timeUnit == null) {
                    return false;
                }
                redisTemplate.opsForHash().putAll(key, map);
                expire(key, time, timeUnit);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_HASH_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 向一张hash表中放入数据,如果不存在将创建
         *
         * @param key   键
         * @param item  项
         * @param value 值
         * @return true 成功 false失败
         */
        public boolean set(String key, String item, Object value) {
            try {
                redisTemplate.opsForHash().put(key, item, value);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_HASH_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 向一张hash表中放入数据,如果不存在将创建
         *
         * @param key   键
         * @param item  项
         * @param value 值
         * @param time  时间(秒)
         * @return true 成功 false失败
         */
        public boolean set(String key, String item, Object value, long time) {
            try {
                redisTemplate.opsForHash().put(key, item, value);
                expire(key, time);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_HASH_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 向一张hash表中放入数据,如果不存在将创建
         *
         * @param key      键
         * @param item     项
         * @param value    值
         * @param time     时间(秒)
         * @param timeUnit 时间单位
         * @return true 成功 false失败
         */
        public boolean set(String key, String item, Object value, long time, TimeUnit timeUnit) {
            try {
                if (time <= 0 || timeUnit == null) {
                    return false;
                }
                redisTemplate.opsForHash().put(key, item, value);
                expire(key, time, timeUnit);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_HASH_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 删除hash表中的值
         *
         * @param key  键
         * @param item 项
         * @return true 成功 false失败
         */
        public boolean del(String key, Object... item) {
            try {
                return redisTemplate.opsForHash().delete(key, item) > 0;
            } catch (Exception e) {
                log.error("RedisService.HashCache.del:", e);
                return false;
            }
        }

        /**
         * 判断hash表中是否有该项的值
         *
         * @param key  键 不能为null
         * @param item 项 不能为null
         * @return true 存在 false不存在
         */
        public boolean hasKey(String key, String item) {
            try {
                return redisTemplate.opsForHash().hasKey(key, item);
            } catch (Exception e) {
                log.error("RedisService.HashCache.hasKey:", e);
                return false;
            }
        }
    }

    public class SetCache {

        /**
         * 根据key获取Set中的所有值
         *
         * @param key 键
         * @return 值
         */
        public Set<Object> get(String key) {
            try {
                return redisTemplate.opsForSet().members(key);
            } catch (Exception e) {
                log.error("RedisService.SetCache.get:", e);
                return Collections.emptySet();
            }
        }

        /**
         * 根据key获取Set中的所有值
         *
         * @param key 键
         * @return 值
         */
        public <T> Set<T> get(String key, Class<T> clazz) {
            try {
                return Convert.toSet(clazz, redisTemplate.opsForSet().members(key));
            } catch (Exception e) {
                log.error("RedisService.SetCache.get:", e);
                return Collections.emptySet();
            }
        }

        /**
         * 根据value从一个set中查询,是否存在
         *
         * @param key   键
         * @param value 值
         * @return true 存在 false不存在
         */
        public boolean hasKey(String key, Object value) {
            try {
                Optional<Boolean> optional = Optional.ofNullable(redisTemplate.opsForSet().isMember(key, value));
                return optional.orElse(false);
            } catch (Exception e) {
                log.error("RedisService.SetCache.hasKey:", e);
                return false;
            }
        }

        /**
         * 将数据放入set缓存
         *
         * @param key    键
         * @param values 值
         * @return 成功个数
         */
        public boolean set(String key, Object... values) {
            try {
                Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForSet().add(key, values));
                return optional.orElse(-1L) > 0;
            } catch (Exception e) {
                log.error("RedisService.SetCache.set:", e);
                return false;
            }
        }

        /**
         * 将set数据放入缓存
         *
         * @param key    键
         * @param time   时间(秒)
         * @param values 值 可以是多个
         * @return 成功个数
         */
        public boolean set(String key, long time, Object... values) {
            try {
                if (time <= 0) {
                    return false;
                }
                Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForSet().add(key, values));
                expire(key, time);
                return optional.orElse(-1L) > 0;
            } catch (Exception e) {
                log.error("RedisService.SetCache.set:", e);
                return false;
            }
        }

        /**
         * 获取set缓存的长度
         *
         * @param key 键
         * @return 长度
         */
        public long size(String key) {
            try {
                return Optional.ofNullable(redisTemplate.opsForSet().size(key)).orElse(0L);
            } catch (Exception e) {
                log.error("RedisService.SetCache.size:", e);
                return 0;
            }
        }

        /**
         * 移除值为value的
         *
         * @param key    键
         * @param values 值 可以是多个
         * @return 移除的个数
         */
        public boolean del(String key, Object... values) {
            try {
                Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForSet().remove(key, values));
                return optional.orElse(-1L) > 0;
            } catch (Exception e) {
                log.error("RedisService.SetCache.del:", e);
                return false;
            }
        }
    }

    public class ListCache {

        /**
         * 获取list缓存的内容
         *
         * @param key 键
         * @return 值
         */
        public List<Object> get(String key) {
            try {
                return redisTemplate.opsForList().range(key, 0, -1);
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_GET, e);
                return Collections.emptyList();
            }
        }

        /**
         * 获取list缓存的内容
         *
         * @param key   键
         * @param start 开始
         * @param end   结束  0 到 - 1 代表所有值
         * @return 值
         */
        public List<Object> get(String key, long start, long end) {
            try {
                return redisTemplate.opsForList().range(key, start, end);
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_GET, e);
                return Collections.emptyList();
            }
        }

        /**
         * 通过索引 获取list中的值
         *
         * @param key   键
         * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
         * @return 值
         */
        public Object get(String key, long index) {
            try {
                return redisTemplate.opsForList().index(key, index);
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_GET, e);
                return null;
            }
        }

        /**
         * 通过索引 获取list中的值
         *
         * @param key   键
         * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
         * @return 值
         */
        public <T> T get(String key, long index, Class<T> clazz) {
            try {
                return getObject(redisTemplate.opsForList().index(key, index), clazz);
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_GET, e);
                return null;
            }
        }

        /**
         * 获取list缓存的长度
         *
         * @param key 键
         * @return 长度
         */
        public long size(String key) {
            try {
                return Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(0L);
            } catch (Exception e) {
                log.error("RedisService.ListCache.size:", e);
                return 0;
            }
        }

        /**
         * 将list放入缓存
         *
         * @param key   键
         * @param value 值
         * @return true成功|false失败
         */
        public boolean set(String key, Object value) {
            try {
                redisTemplate.opsForList().rightPush(key, value);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 将list放入缓存
         *
         * @param key   键
         * @param value 值
         * @param time  时间(秒)
         * @return true成功|false失败
         */
        public boolean set(String key, Object value, long time) {
            try {
                if (time <= 0) {
                    return false;
                }
                redisTemplate.opsForList().rightPush(key, value);
                expire(key, time);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 将list放入缓存
         *
         * @param key      键
         * @param value    值
         * @param time     时间
         * @param timeUnit 时间单位
         * @return true成功|false失败
         */
        public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
            try {
                if (time <= 0 || timeUnit == null) {
                    return false;
                }
                redisTemplate.opsForList().rightPush(key, value);
                expire(key, time, timeUnit);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 将list放入缓存
         *
         * @param key   键
         * @param value 值
         * @return true成功|false失败
         */
        public boolean set(String key, List<Object> value) {
            try {
                redisTemplate.opsForList().rightPushAll(key, value);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 将list放入缓存
         *
         * @param key   键
         * @param value 值
         * @param time  时间(秒)
         * @return true成功|false失败
         */
        public boolean set(String key, List<Object> value, long time) {
            try {
                if (time <= 0) {
                    return false;
                }
                redisTemplate.opsForList().rightPushAll(key, value);
                expire(key, time);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 根据索引修改list中的某条数据
         *
         * @param key   键
         * @param index 索引
         * @param value 值
         * @return true成功|false失败
         */
        public boolean set(String key, long index, Object value) {
            try {
                redisTemplate.opsForList().set(key, index, value);
                return true;
            } catch (Exception e) {
                log.error(REDIS_SERVICE_LIST_CACHE_SET, e);
                return false;
            }
        }

        /**
         * 移除N个值为value
         *
         * @param key   键
         * @param count 移除多少个
         * @param value 值
         * @return true成功|false失败
         */
        public boolean del(String key, long count, Object value) {
            try {
                Optional<Long> optional = Optional.ofNullable(redisTemplate.opsForList().remove(key, count, value));
                return optional.orElse(-1L) > 0;
            } catch (Exception e) {
                log.error("RedisService.ListCache.del:", e);
                return false;
            }
        }
    }
}
