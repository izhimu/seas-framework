package com.izhimu.seas.cache.connector;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.extra.cglib.CglibUtil;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.Value;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Redis连接器
 *
 * @author haoran
 * @version 1.0
 */
@SuppressWarnings("unused")
@Slf4j
public class RedisConnector {

    public static final String REDIS_SERVICE_SET = "RedisService.set:";
    public static final String REDIS_SERVICE_HASH_CACHE_SET = "RedisService.HashCache.set:";
    public static final String REDIS_SERVICE_LIST_CACHE_GET = "RedisService.ListCache.get:";
    public static final String REDIS_SERVICE_LIST_CACHE_SET = "RedisService.ListCache.set:";

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

    private final RedisClient client;

    public RedisConnector(String host, int port) {
        RedisURI uri = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withDatabase(0)
                .build();
        this.client = RedisClient.create(uri);
        this.hashCache = new HashCache();
        this.setCache = new SetCache();
        this.listCache = new ListCache();
    }

    public RedisConnector(String host, int port, int database) {
        RedisURI uri = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withDatabase(database)
                .build();
        this.client = RedisClient.create(uri);
        this.hashCache = new HashCache();
        this.setCache = new SetCache();
        this.listCache = new ListCache();
    }

    public RedisConnector(String host, int port, int database, String password) {
        RedisURI uri = RedisURI.builder()
                .withHost(host)
                .withPort(port)
                .withPassword(new StringBuilder(password))
                .withDatabase(database)
                .build();
        this.client = RedisClient.create(uri);
        this.hashCache = new HashCache();
        this.setCache = new SetCache();
        this.listCache = new ListCache();
    }

    public <T> T connect(Function<RedisCommands<String, Object>, T> consumer) {
        try (StatefulRedisConnection<String, Object> connect = client.connect(new KryoRedisCodec())) {
            RedisCommands<String, Object> sync = connect.sync();
            return consumer.apply(sync);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
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
        if (time <= 0) {
            return false;
        }
        return connect(v -> v.expire(key, time));
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
        if (time <= 0) {
            return false;
        }
        if (TimeUnit.MILLISECONDS.equals(timeUnit) && time < 1000) {
            return expire(key, 1);
        }
        return expire(key, timeUnit.toSeconds(time));
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键
     * @return 时间(秒) 0 永久有效|1 错误
     */
    public long getExpire(String key) {
        return TimeUnit.MILLISECONDS.toSeconds(connect(v -> v.pttl(key)));
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true存在|false不存在
     */
    public boolean hasKey(String key) {
        return longToBoolean(connect(v -> v.exists(key)));
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public boolean del(List<String> key) {
        if (key == null || key.size() == 0) {
            return false;
        }
        return longToBoolean(connect(v -> v.del(key.toArray(new String[]{}))));
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return connect(v -> v.get(key));
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key, Class<T> clazz) {
        return getObject(get(key), clazz);
    }

    /**
     * 普通缓存获取
     *
     * @param keys 键
     * @return 值
     */
    public List<Object> get(List<String> keys) {
        List<KeyValue<String, Object>> result = connect(v -> v.mget(keys.toArray(new String[]{})));
        return result.stream()
                .map(Value::getValue)
                .collect(Collectors.toList());
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key, TypeReference<T> reference) {
        return getObject(get(key), reference);
    }

    /**
     * 普通缓存获取
     *
     * @param keys 键
     * @return 值
     */
    public <T> List<T> get(List<String> keys, Supplier<T> target) {
        return getObject(get(keys), target);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功|false失败
     */
    public boolean set(String key, Object value) {
        return stringToBoolean(connect(v -> v.set(key, value)));
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
        return set(key, value, time, TimeUnit.SECONDS);
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
        return stringToBoolean(connect(v -> v.psetex(key, timeUnit.toMillis(time), value)));
    }

    /**
     * 不存在时放入
     *
     * @param key   键
     * @param value 值
     * @return boolean
     */
    public boolean setIfAbsent(String key, Object value) {
        return connect(v -> v.setnx(key, value));
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

    /**
     * 泛型对象转换
     *
     * @param obj       对象
     * @param reference 类型
     * @param <T>       类型
     * @return 转换对象
     */
    private <T> T getObject(Object obj, TypeReference<T> reference) {
        return Convert.convert(reference, obj);
    }

    /**
     * 泛型对象转换
     *
     * @param objs   对象
     * @param target 类型
     * @param <T>    类型
     * @return 转换对象
     */
    private <T> List<T> getObject(List<Object> objs, Supplier<T> target) {
        if (Objects.isNull(objs)) {
            return Collections.emptyList();
        }
        return CglibUtil.copyList(objs, target);
    }

    /**
     * long to boolean
     *
     * @param data Long
     * @return boolean
     */
    private boolean longToBoolean(Long data) {
        return Optional.ofNullable(data).orElse(0L) > 0;
    }

    /**
     * string to boolean
     *
     * @param data String
     * @return boolean
     */
    private boolean stringToBoolean(String data) {
        return "OK".equals(data);
    }

    @SuppressWarnings("unused")
    public class HashCache {

        /**
         * HashGet
         *
         * @param key  键
         * @param item 项
         * @return 值
         */
        public Object get(String key, String item) {
            return connect(v -> v.hget(key, item));
        }

        /**
         * HashGet
         *
         * @param key  键
         * @param item 项
         * @return 值
         */
        public <T> T get(String key, String item, Class<T> clazz) {
            return getObject(get(key, item), clazz);
        }

        /**
         * 获取hashKey对应的所有键值
         *
         * @param key 键
         * @return 对应的多个键值
         */
        public Map<String, Object> getValuesToMap(String key) {
            return connect(v -> v.hgetall(key));
        }

        /**
         * 获取hashKey对应的所有键值
         *
         * @param key 键
         * @return 对应的多个键值
         */
        public List<Object> getValues(String key) {
            return connect(v -> v.hvals(key));
        }

        /**
         * HashSet
         *
         * @param key 键
         * @param map 对应多个键值
         * @return true 成功|false 失败
         */
        public boolean set(String key, Map<String, Object> map) {
            return stringToBoolean(connect(v -> v.hmset(key, map)));
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
            return connect(v -> stringToBoolean(v.hmset(key, map)) && v.expire(key, time));
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
            if (TimeUnit.MILLISECONDS.equals(timeUnit) && time < 1000) {
                return set(key, map, 1);
            }
            return set(key, map, timeUnit.toSeconds(time));
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
            return connect(v -> v.hset(key, item, value));
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
            return connect(v -> v.hset(key, item, value) && v.expire(key, time));
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
            if (TimeUnit.MILLISECONDS.equals(timeUnit) && time < 1000) {
                return set(key, item, value, 1);
            }
            return set(key, item, value, timeUnit.toSeconds(time));
        }

        /**
         * 删除hash表中的值
         *
         * @param key  键
         * @param item 项
         * @return true 成功 false失败
         */
        public boolean del(String key, List<String> item) {
            return longToBoolean(connect(v -> v.hdel(key, item.toArray(new String[]{}))));
        }

        /**
         * 判断hash表中是否有该项的值
         *
         * @param key  键 不能为null
         * @param item 项 不能为null
         * @return true 存在 false不存在
         */
        public boolean hasKey(String key, String item) {
            return connect(v -> v.hexists(key, item));
        }
    }

    @SuppressWarnings("unused")
    public class SetCache {

        /**
         * 根据key获取Set中的所有值
         *
         * @param key 键
         * @return 值
         */
        public Set<Object> get(String key) {
            return connect(v -> v.smembers(key));
        }

        /**
         * 根据key获取Set中的所有值
         *
         * @param key 键
         * @return 值
         */
        public <T> Set<T> get(String key, Class<T> clazz) {
            return Convert.toSet(clazz, get(key));
        }

        /**
         * 根据value从一个set中查询,是否存在
         *
         * @param key   键
         * @param value 值
         * @return true 存在 false不存在
         */
        public boolean hasKey(String key, Object value) {
            return connect(v -> v.sismember(key, value));
        }

        /**
         * 将数据放入set缓存
         *
         * @param key    键
         * @param values 值
         * @return 成功个数
         */
        public boolean set(String key, List<Object> values) {
            return longToBoolean(connect(v -> v.sadd(key, values.toArray())));
        }

        /**
         * 将set数据放入缓存
         *
         * @param key    键
         * @param time   时间(秒)
         * @param values 值 可以是多个
         * @return 成功个数
         */
        public boolean set(String key, long time, List<Object> values) {
            return connect(v -> longToBoolean(v.sadd(key, values.toArray())) && v.expire(key, time));
        }

        /**
         * 取出并删除
         *
         * @param key String
         * @return Object
         */
        public Object pop(String key) {
            return connect(v -> v.spop(key));
        }

        /**
         * 取出并删除
         *
         * @param key String
         * @return Object
         */
        public Set<Object> pop(String key, long count) {
            return connect(v -> v.spop(key, count));
        }

        /**
         * 获取set缓存的长度
         *
         * @param key 键
         * @return 长度
         */
        public long size(String key) {
            return connect(v -> v.scard(key));
        }

        /**
         * 移除值为value的
         *
         * @param key    键
         * @param values 值 可以是多个
         * @return 移除的个数
         */
        public boolean del(String key, List<Object> values) {
            return connect(v -> longToBoolean(v.srem(key, values.toArray())));
        }
    }

    @SuppressWarnings("unused")
    public class ListCache {

        /**
         * 获取list缓存的内容
         *
         * @param key 键
         * @return 值
         */
        public List<Object> get(String key) {
            return connect(v -> v.lrange(key, 0, -1));
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
            return connect(v -> v.lrange(key, start, end));
        }

        /**
         * 通过索引 获取list中的值
         *
         * @param key   键
         * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
         * @return 值
         */
        public Object get(String key, long index) {
            return connect(v -> v.lindex(key, index));
        }

        /**
         * 通过索引 获取list中的值
         *
         * @param key   键
         * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
         * @return 值
         */
        public <T> T get(String key, long index, Class<T> clazz) {
            return getObject(get(key, index), clazz);
        }

        /**
         * 获取list缓存的长度
         *
         * @param key 键
         * @return 长度
         */
        public long size(String key) {
            return connect(v -> v.llen(key));
        }

        /**
         * 将list放入缓存
         *
         * @param key   键
         * @param value 值
         * @return true成功|false失败
         */
        public boolean set(String key, Object value) {
            return longToBoolean(connect(v -> v.rpush(key, value)));
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
            return connect(v -> longToBoolean(v.rpush(key, value)) && v.expire(key, time));
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
            return connect(v -> longToBoolean(v.rpush(key, value)) && v.expire(key, timeUnit.toSeconds(time)));
        }

        /**
         * 将list放入缓存
         *
         * @param key   键
         * @param value 值
         * @return true成功|false失败
         */
        public boolean set(String key, List<Object> value) {
            return longToBoolean(connect(v -> v.rpush(key, value.toArray())));
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
            return connect(v -> longToBoolean(v.rpush(key, value.toArray())) && v.expire(key, time));
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
            return stringToBoolean(connect(v -> v.lset(key, index, value)));
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
            return longToBoolean(connect(v -> v.lrem(key, count, value)));
        }
    }
}
