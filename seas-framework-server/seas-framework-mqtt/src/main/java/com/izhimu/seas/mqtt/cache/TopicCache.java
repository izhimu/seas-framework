package com.izhimu.seas.mqtt.cache;

import com.izhimu.seas.cache.service.SetCacheService;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.izhimu.seas.mqtt.constant.MQTTConst.*;

/**
 * 主题缓存
 *
 * @author haoran
 */
@UtilityClass
public class TopicCache {

    private static final String RULE_CACHE_KEY = "mqtt:rule:";
    private static final String TOPIC_CACHE_KEY = "mqtt:topic";
    private static final int CACHE_DAY = 7 * 24 * 60 * 60;

    static SetCacheService setCacheService;

    /**
     * 添加主题缓存
     *
     * @param topic 主题
     */
    public static void put(String topic) {
        setCacheService.setExpire(TOPIC_CACHE_KEY, CACHE_DAY, topic);
    }

    /**
     * 获取主题缓存
     *
     * @return 主题规则
     */
    public static Set<String> keys() {
        return setCacheService.get(TOPIC_CACHE_KEY, String.class);
    }

    /**
     * 添加规则缓存
     *
     * @param topic 主题
     */
    public static void putRule(String topic) {
        setCacheService.setExpire(getKey(topic), CACHE_DAY, ruleMatch(topic).toArray());
        put(topic);
    }

    /**
     * 获取规则缓存
     *
     * @param topic 主题
     * @return 主题规则
     */
    public static Set<String> getRule(String topic) {
        return setCacheService.get(getKey(topic), String.class);
    }

    /**
     * 规则缓存Keu
     *
     * @param topic key
     * @return key
     */
    private String getKey(String topic) {
        return RULE_CACHE_KEY + topic;
    }

    /**
     * 根据匹配规则刷新规则缓存
     *
     * @param topicRule 匹配规则
     */
    public static void refreshRule(String topicRule, boolean isDel) {
        Pattern pattern = Pattern.compile("^" + topicRule.replace(SINGLE_LAYER_SYMBOL, SINGLE_LAYER_PATTERN)
                .replace(MULTI_LAYER_SYMBOL, MULTI_LAYER_PATTERN) + "$");
        keys().stream()
                .filter(t -> pattern.matcher(t).matches())
                .forEach(t -> {
                    if (isDel) {
                        setCacheService.del(getKey(t), topicRule);
                    } else {
                        setCacheService.setExpire(getKey(t), CACHE_DAY, topicRule);
                    }
                });
    }

    /**
     * 规则匹配
     *
     * @param topic 主题
     * @return 规则
     */
    private static Set<String> ruleMatch(String topic) {
        Set<String> rule = SubscribeCache.rules();
        return rule.stream()
                .filter(r -> {
                    Pattern pattern = Pattern.compile("^" + r.replace(SINGLE_LAYER_SYMBOL, SINGLE_LAYER_PATTERN)
                            .replace(MULTI_LAYER_SYMBOL, MULTI_LAYER_PATTERN) + "$");
                    return pattern.matcher(topic).matches();
                })
                .collect(Collectors.toSet());
    }
}
