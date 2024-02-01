package com.izhimu.seas.mqtt.cache;

import com.izhimu.seas.cache.service.SetCacheService;
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
     * @param setCacheService SetCacheService
     */
    public static void init(SetCacheService setCacheService) {
        TopicCache.setCacheService = setCacheService;
        SubscribeCache.setCacheService = setCacheService;
    }
}
