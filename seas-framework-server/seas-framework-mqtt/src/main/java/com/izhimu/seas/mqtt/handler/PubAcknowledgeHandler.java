package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPubAckMessage;

import java.util.Map;

/**
 * QoS 1 完成处理
 *
 * @author haoran
 */
public record PubAcknowledgeHandler(MqttEndpoint endpoint) implements Handler<MqttPubAckMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    @Override
    public void handle(MqttPubAckMessage event) {
        log.info(Map.of("clientId", endpoint.clientIdentifier()), "Received ack for message = " + event.messageId());
    }
}
