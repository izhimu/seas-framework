package com.izhimu.seas.mqtt.cache;

import com.izhimu.seas.cache.service.SetCacheService;
import com.izhimu.seas.mqtt.entity.SubscribeInfo;
import lombok.experimental.UtilityClass;

import java.util.Set;

/**
 * 订阅缓存
 * topicRule -> subscribeInfo Set
 * topicRule Set
 *
 * @author haoran
 */
@UtilityClass
public class SubscribeCache {

    private static final String SUBSCRIBE_CACHE_KEY = "mqtt:subscribe:";
    private static final String SUBSCRIBE_TOPIC_CACHE_KEY = "mqtt:subscribe.topic";

    static SetCacheService setCacheService;

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
        setCacheService.set(getKey(topicRule), subscribeInfo);
        putRule(topicRule);
        TopicCache.refreshRule(topicRule, false);
    }

    /**
     * 获取缓存
     *
     * @param topicRule 订阅规则
     * @return 订阅列表
     */
    public static Set<SubscribeInfo> get(String topicRule) {
        return setCacheService.get(getKey(topicRule), SubscribeInfo.class);
    }

    /**
     * 删除缓存
     *
     * @param topicRule     订阅规则
     * @param subscribeInfo 订阅信息
     */
    public static void del(String topicRule, SubscribeInfo subscribeInfo) {
        setCacheService.del(getKey(topicRule), subscribeInfo);
        delRule(topicRule);
        TopicCache.refreshRule(topicRule, true);
    }

    /**
     * 添加规则缓存
     *
     * @param topicRule 订阅规则
     */
    public static void putRule(String topicRule) {
        setCacheService.set(SUBSCRIBE_TOPIC_CACHE_KEY, topicRule);
    }

    /**
     * 删除规则缓存
     *
     * @param topicRule 订阅规则
     */
    public static void delRule(String topicRule) {
        setCacheService.del(SUBSCRIBE_TOPIC_CACHE_KEY, topicRule);
    }

    /**
     * 获取全部订阅规则
     *
     * @return 订阅规则
     */
    public static Set<String> rules() {
        return setCacheService.get(SUBSCRIBE_TOPIC_CACHE_KEY, String.class);
    }
}
