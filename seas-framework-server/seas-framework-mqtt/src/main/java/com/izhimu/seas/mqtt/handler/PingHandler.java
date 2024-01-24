package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;

import java.util.Map;

/**
 * Ping消息处理
 *
 * @author haoran
 */
public record PingHandler(MqttEndpoint endpoint) implements Handler<Void> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    @Override
    public void handle(Void unused) {
        log.info(Map.of("clientId", endpoint.clientIdentifier()), "Ping received from client");
    }
}
