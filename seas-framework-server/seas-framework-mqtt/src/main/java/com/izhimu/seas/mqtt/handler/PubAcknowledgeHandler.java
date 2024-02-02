package com.izhimu.seas.mqtt.handler;

import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPubAckMessage;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * QoS 1 完成处理
 *
 * @author haoran
 */
public record PubAcknowledgeHandler(MqttEndpoint endpoint) implements Handler<MqttPubAckMessage> {

    @Override
    public void handle(MqttPubAckMessage event) {
        log.debugT(endpoint.clientIdentifier(), "[MQTT Server] received ack for message, id: {}", event.messageId());
    }
}
