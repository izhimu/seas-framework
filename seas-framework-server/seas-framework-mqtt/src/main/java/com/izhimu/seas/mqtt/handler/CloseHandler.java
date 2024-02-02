package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.mqtt.cache.ClientCache;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 客户端关闭处理
 *
 * @author haoran
 */
public record CloseHandler(MqttEndpoint endpoint) implements Handler<Void> {
    @Override
    public void handle(Void event) {
        log.infoT(endpoint.clientIdentifier(), "[MQTT Server] client close");
        ClientCache.del(endpoint.clientIdentifier());
    }
}
