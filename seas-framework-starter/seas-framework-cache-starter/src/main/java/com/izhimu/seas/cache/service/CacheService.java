package com.izhimu.seas.cache.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务接口
 *
 * @author haoran
 */
@SuppressWarnings("unused")
public interface CacheService extends CacheConvertService {

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true存在|false不存在
     */
    boolean hasKey(String key);

    /**
     * 删除缓存
     *
     * @param key 键
     */
    boolean del(String... key);

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功|false失败
     */
    boolean set(String key, Object value);

    /**
     * 失效时间缓存放入
     *
     * @param key   键
     * @param value 值
     * @param time  时间
     * @return true成功 false 失败
     */
    boolean set(String key, Object value, long time);

    /**
     * 失效时间缓存放入
     *
     * @param key      键
     * @param value    值
     * @param time     时间
     * @param timeUnit 时间单位
     * @return true成功 false 失败
     */
    boolean set(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 不存在时放入
     *
     * @param key   String
     * @param value Object
     * @return boolean
     */
    boolean setIfAbsent(String key, Object value);

    /**
     * 不存在时放入
     *
     * @param key   String
     * @param value Object
     * @return boolean
     */
    boolean setIfAbsent(String key, Object value, long time);

    /**
     * 不存在时放入
     *
     * @param key   String
     * @param value Object
     * @return boolean
     */
    boolean setIfAbsent(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return true成功|false失败
     */
    boolean setExpire(String key, long time);

    /**
     * 指定缓存失效时间
     *
     * @param key      键
     * @param time     时间(秒)
     * @param timeUnit 时间单位
     * @return true成功|false失败
     */
    boolean setExpire(String key, long time, TimeUnit timeUnit);

    /**
     * 根据key 获取过期时间
     *
     * @param key 键
     * @return 时间(秒) 0 永久有效|1 错误
     */
    long getExpire(String key);

    /**
     * 获取所有Key
     *
     * @param pattern 查询参数
     * @return 所有Key
     */
    Set<String> keys(String pattern);
}
