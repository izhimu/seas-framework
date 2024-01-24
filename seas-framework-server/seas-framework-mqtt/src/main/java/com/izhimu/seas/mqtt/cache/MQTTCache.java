package com.izhimu.seas.mqtt.cache;

import com.izhimu.seas.cache.service.CacheService;
import lombok.experimental.UtilityClass;

/**
 * MQTT缓存
 *
 * @author haoran
 */
@UtilityClass
public class MQTTCache {

    /**
     * 初始化
     *
     * @param cacheService CacheService
     */
    public static void init(CacheService cacheService) {
        TopicCache.cacheService = cacheService;
        SubscribeCache.cacheService = cacheService;
    }
}
