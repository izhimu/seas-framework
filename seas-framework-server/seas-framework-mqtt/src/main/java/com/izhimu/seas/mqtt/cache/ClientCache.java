package com.izhimu.seas.mqtt.cache;

import io.vertx.mqtt.MqttEndpoint;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 客户端缓存
 * clientId -> endpoint
 *
 * @author haoran
 */
@UtilityClass
public class ClientCache {

    private static final Map<String, MqttEndpoint> CLIENT_CACHE = new ConcurrentHashMap<>();

    /**
     * 添加客户端
     *
     * @param endpoint MqttEndpoint
     */
    public static void put(MqttEndpoint endpoint) {
        CLIENT_CACHE.put(endpoint.clientIdentifier(), endpoint);
        log.debug("ClientCache add client, clientId:{}", endpoint.clientIdentifier());
    }

    /**
     * 获取客户端
     *
     * @param clientId 客户端ID
     * @return MqttEndpoint
     */
    public static MqttEndpoint get(String clientId) {
        return CLIENT_CACHE.get(clientId);
    }

    /**
     * 删除客户端
     *
     * @param clientId 客户端ID
     */
    public static void del(String clientId) {
        CLIENT_CACHE.remove(clientId);
        log.debug("ClientCache del client, clientId:{}", clientId);
    }
}
