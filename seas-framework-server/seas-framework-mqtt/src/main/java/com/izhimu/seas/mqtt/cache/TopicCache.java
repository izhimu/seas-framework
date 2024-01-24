package com.izhimu.seas.mqtt.cache;

import com.izhimu.seas.cache.service.CacheService;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.izhimu.seas.mqtt.constant.MQTTConst.*;

/**
 * 规则缓存
 *
 * @author haoran
 */
@UtilityClass
public class TopicCache {

    private static final String RULE_CACHE_KEY = "mqtt:rule:";
    private static final String TOPIC_CACHE_KEY = "mqtt:topic";

    static CacheService cacheService;

    /**
     * 添加主题缓存
     *
     * @param topic 主题
     */
    public static void put(String topic) {
        if (cacheService.hasKey(TOPIC_CACHE_KEY)) {
            //noinspection unchecked
            Set<String> set = cacheService.get(TOPIC_CACHE_KEY, Set.class);
            set.add(topic);
            cacheService.set(TOPIC_CACHE_KEY, set, 7, TimeUnit.DAYS);
        } else {
            Set<String> set = new HashSet<>();
            set.add(topic);
            cacheService.set(TOPIC_CACHE_KEY, set, 7, TimeUnit.DAYS);
        }
    }

    /**
     * 获取主题缓存
     *
     * @return 主题规则
     */
    public static Set<String> keys() {
        Object o = cacheService.get(TOPIC_CACHE_KEY);
        if (Objects.isNull(o)) {
            return Collections.emptySet();
        }
        //noinspection unchecked
        return (Set<String>) o;
    }

    /**
     * 添加规则缓存
     *
     * @param topic 主题
     */
    public static void putRule(String topic) {
        String key = RULE_CACHE_KEY + topic;
        if (cacheService.hasKey(key)) {
            cacheService.setExpire(key, 7, TimeUnit.DAYS);
        } else {
            cacheService.set(key, ruleMatch(topic), 7, TimeUnit.DAYS);
        }
    }

    /**
     * 获取规则缓存
     *
     * @param topic 主题
     * @return 主题规则
     */
    public static Set<String> getRule(String topic) {
        Object o = cacheService.get(RULE_CACHE_KEY + topic);
        if (Objects.isNull(o)) {
            return Collections.emptySet();
        }
        //noinspection unchecked
        return (Set<String>) o;
    }

    /**
     * 根据匹配规则刷新规则缓存
     *
     * @param topicRule 匹配规则
     */
    public static void refreshRule(String topicRule) {
        // TODO
    }

    /**
     * 规则匹配
     *
     * @param topic 主题
     * @return 规则
     */
    private static Set<String> ruleMatch(String topic) {
        Set<String> rule = SubscribeCache.keys();
        return rule.stream()
                .filter(r -> {
                    Pattern pattern = Pattern.compile("^" + r.replace(SINGLE_LAYER_SYMBOL, SINGLE_LAYER_PATTERN)
                            .replace(MULTI_LAYER_SYMBOL, MULTI_LAYER_PATTERN) + "$");
                    return pattern.matcher(topic).matches();
                })
                .collect(Collectors.toSet());
    }
}
