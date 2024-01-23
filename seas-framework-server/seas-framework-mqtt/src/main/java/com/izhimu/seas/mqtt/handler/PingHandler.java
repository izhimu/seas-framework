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
public class PingHandler implements Handler<Void> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    private final MqttEndpoint endpoint;

    public PingHandler(MqttEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void handle(Void unused) {
        log.info(Map.of("clientId", endpoint.clientIdentifier()), "Ping received from client");
    }
}
