package com.izhimu.seas.cache.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * List数据类型缓存服务接口
 *
 * @author haoran
 */
@SuppressWarnings("unused")
public interface ListCacheService extends CacheConvertService {

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     * @return 值
     */
    List<Object> get(String key);

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 - 1 代表所有值
     * @return 值
     */
    List<Object> get(String key, long start, long end);

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 值
     */
    Object get(String key, long index);

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return 值
     */
    <T> T get(String key, long index, Class<T> clazz);

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return 长度
     */
    long size(String key);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功|false失败
     */
    boolean set(String key, Object value);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return true成功|false失败
     */
    boolean set(String key, Object value, long time);

    /**
     * 将list放入缓存
     *
     * @param key      键
     * @param value    值
     * @param time     时间
     * @param timeUnit 时间单位
     * @return true成功|false失败
     */
    boolean set(String key, Object value, long time, TimeUnit timeUnit);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功|false失败
     */
    boolean set(String key, List<Object> value);

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return true成功|false失败
     */
    boolean set(String key, List<Object> value, long time);

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return true成功|false失败
     */
    boolean set(String key, long index, Object value);

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return true成功|false失败
     */
    boolean del(String key, long count, Object value);
}
