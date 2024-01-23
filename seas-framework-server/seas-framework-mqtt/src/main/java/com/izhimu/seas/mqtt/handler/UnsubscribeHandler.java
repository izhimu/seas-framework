package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttUnsubscribeMessage;

/**
 * 退订处理
 *
 * @author haoran
 */
public class UnsubscribeHandler implements Handler<MqttUnsubscribeMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    private final MqttEndpoint endpoint;

    public UnsubscribeHandler(MqttEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void handle(MqttUnsubscribeMessage unsubscribe) {
        for (String t : unsubscribe.topics()) {
            log.info("Unsubscription for " + t);
        }
        endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
    }
}
