package com.izhimu.seas.mqtt.cache;

import com.izhimu.seas.cache.service.CacheService;
import com.izhimu.seas.mqtt.entity.SubscribeInfo;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 订阅缓存
 *
 * @author haoran
 */
@UtilityClass
public class SubscribeCache {

    private static final String SUBSCRIBE_CACHE_KEY = "mqtt:subscribe:";
    private static final String SUBSCRIBE_TOPIC_CACHE_KEY = "mqtt:subscribe.topic";

    static CacheService cacheService;

    /**
     * 获取Key
     *
     * @param topicRule 规则
     * @return key
     */
    public static String getKey(String topicRule) {
        return SUBSCRIBE_CACHE_KEY + topicRule;
    }

    /**
     * 添加缓存
     *
     * @param topicRule     订阅规则
     * @param subscribeInfo 订阅信息
     */
    public static void put(String topicRule, SubscribeInfo subscribeInfo) {
        String key = getKey(topicRule);
        if (cacheService.hasKey(key)) {
            //noinspection unchecked
            Set<SubscribeInfo> set = cacheService.get(key, Set.class);
            set.add(subscribeInfo);
            cacheService.set(key, set);
        } else {
            Set<SubscribeInfo> set = new HashSet<>();
            set.add(subscribeInfo);
            cacheService.set(key, set);
        }
        putRule(topicRule);
    }

    /**
     * 获取缓存
     *
     * @param topicRule 订阅规则
     * @return 订阅列表
     */
    public static Set<SubscribeInfo> get(String topicRule) {
        Object o = cacheService.get(getKey(topicRule));
        if (Objects.isNull(o)) {
            return Collections.emptySet();
        }
        //noinspection unchecked
        return (Set<SubscribeInfo>) o;
    }

    /**
     * 删除缓存
     *
     * @param topicRule     订阅规则
     * @param subscribeInfo 订阅信息
     */
    public static void del(String topicRule, SubscribeInfo subscribeInfo) {
        String key = getKey(topicRule);
        Object o = cacheService.get(key);
        if (Objects.isNull(o)) {
            return;
        }
        //noinspection unchecked
        Set<SubscribeInfo> set = (Set<SubscribeInfo>) o;
        set.remove(subscribeInfo);
        if (set.isEmpty()) {
            cacheService.del(key);
        } else {
            cacheService.set(key, set);
        }
        delRule(topicRule);
    }

    /**
     * 添加规则缓存
     *
     * @param topicRule 订阅规则
     */
    public static void putRule(String topicRule) {
        if (cacheService.hasKey(SUBSCRIBE_TOPIC_CACHE_KEY)) {
            //noinspection unchecked
            Set<String> set = cacheService.get(SUBSCRIBE_TOPIC_CACHE_KEY, Set.class);
            set.add(topicRule);
            cacheService.set(SUBSCRIBE_TOPIC_CACHE_KEY, set);
        } else {
            Set<String> set = new HashSet<>();
            set.add(topicRule);
            cacheService.set(SUBSCRIBE_TOPIC_CACHE_KEY, set);
        }
    }

    /**
     * 删除规则缓存
     *
     * @param topicRule 订阅规则
     */
    public static void delRule(String topicRule) {
        Object o = cacheService.get(SUBSCRIBE_TOPIC_CACHE_KEY);
        if (Objects.isNull(o)) {
            return;
        }
        //noinspection unchecked
        Set<String> set = (Set<String>) o;
        set.remove(topicRule);
        if (set.isEmpty()) {
            cacheService.del(SUBSCRIBE_TOPIC_CACHE_KEY);
        } else {
            cacheService.set(SUBSCRIBE_TOPIC_CACHE_KEY, set);
        }
    }

    /**
     * 获取全部订阅规则
     *
     * @return 订阅规则
     */
    public static Set<String> keys() {
        Object o = cacheService.get(SUBSCRIBE_TOPIC_CACHE_KEY);
        if (Objects.isNull(o)) {
            return Collections.emptySet();
        }
        //noinspection unchecked
        return (Set<String>) o;
    }
}
