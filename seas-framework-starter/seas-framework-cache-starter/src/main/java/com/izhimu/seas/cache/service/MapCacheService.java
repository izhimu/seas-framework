package com.izhimu.seas.cache.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Map数据类型缓存服务接口
 *
 * @author haoran
 */
@SuppressWarnings("unused")
public interface MapCacheService extends CacheConvertService {

    /**
     * 获取
     *
     * @param key  键
     * @param item 项
     * @return 值
     */
    Object get(String key, String item);

    /**
     * 获取
     *
     * @param key  键
     * @param item 项
     * @return 值
     */
    <T> T get(String key, String item, Class<T> clazz);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    Map<Object, Object> getToMap(String key);

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    List<Object> getValues(String key);

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功|false 失败
     */
    boolean set(String key, Map<String, Object> map);

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    boolean set(String key, Map<String, Object> map, long time);

    /**
     * HashSet 并设置时间
     *
     * @param key      键
     * @param map      对应多个键值
     * @param time     时间
     * @param timeUnit 时间单位
     * @return true成功 false失败
     */
    boolean set(String key, Map<String, Object> map, long time, TimeUnit timeUnit);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    boolean set(String key, String item, Object value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)
     * @return true 成功 false失败
     */
    boolean set(String key, String item, Object value, long time);

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
    boolean set(String key, String item, Object value, long time, TimeUnit timeUnit);

    /**
     * 删除hash表中的值
     *
     * @param key  键
     * @param item 项
     * @return true 成功 false失败
     */
    boolean del(String key, Object... item);

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    boolean hasKey(String key, String item);
}
