package com.izhimu.seas.cache.service;

import java.util.Set;

/**
 * Set数据类型缓存服务接口
 *
 * @author haoran
 */
@SuppressWarnings("unused")
public interface SetCacheService extends CacheConvertService {
    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 值
     */
    Set<Object> get(String key);

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return 值
     */
    <T> Set<T> get(String key, Class<T> clazz);

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    boolean hasKey(String key, Object value);

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值
     * @return 成功个数
     */
    boolean set(String key, Object... values);

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    boolean set(String key, long time, Object... values);

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    long size(String key);

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    boolean del(String key, Object... values);
}
