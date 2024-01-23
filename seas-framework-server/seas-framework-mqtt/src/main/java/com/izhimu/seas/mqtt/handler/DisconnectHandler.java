package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttDisconnectMessage;

import java.util.Map;

/**
 * 断开连接处理
 *
 * @author haoran
 */
public class DisconnectHandler implements Handler<MqttDisconnectMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    private final MqttEndpoint endpoint;

    public DisconnectHandler(MqttEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void handle(MqttDisconnectMessage message) {
        log.info(Map.of("clientId", endpoint.clientIdentifier()), "client disconnected");
    }
}
