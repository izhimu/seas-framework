package com.izhimu.seas.mqtt.handler;

import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPubCompMessage;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * QoS 2 完成处理
 *
 * @author haoran
 */
public record PubCompletionHandler(MqttEndpoint endpoint) implements Handler<MqttPubCompMessage> {

    @Override
    public void handle(MqttPubCompMessage event) {
        log.infoT(endpoint.clientIdentifier(), "received comp for message, id={}", event.messageId());
    }
}
