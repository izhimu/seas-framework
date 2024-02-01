package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.mqtt.cache.ClientCache;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttDisconnectMessage;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 断开连接处理
 *
 * @author haoran
 */
public record DisconnectHandler(MqttEndpoint endpoint) implements Handler<MqttDisconnectMessage> {

    @Override
    public void handle(MqttDisconnectMessage message) {
        log.infoT(endpoint.clientIdentifier(), "client disconnected");
        ClientCache.del(endpoint.clientIdentifier());
    }
}
