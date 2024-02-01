package com.izhimu.seas.mqtt.handler;

import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * Ping消息处理
 *
 * @author haoran
 */
public record PingHandler(MqttEndpoint endpoint) implements Handler<Void> {


    @Override
    public void handle(Void unused) {
        log.debugT(endpoint.clientIdentifier(), "MQTT Server -> ping received from client");
    }
}
