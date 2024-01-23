package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPubCompMessage;

import java.util.Map;

/**
 * QoS 2 完成处理
 *
 * @author haoran
 */
public class PubCompletionHandler implements Handler<MqttPubCompMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    private final MqttEndpoint endpoint;

    public PubCompletionHandler(MqttEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void handle(MqttPubCompMessage event) {
        log.info(Map.of("clientId", endpoint.clientIdentifier()), "Received ack for message = " + event.messageId());
    }
}
