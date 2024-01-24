package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.mqtt.cache.SubscribeCache;
import com.izhimu.seas.mqtt.entity.SubscribeInfo;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttUnsubscribeMessage;

/**
 * 退订处理
 *
 * @author haoran
 */
public record UnsubscribeHandler(MqttEndpoint endpoint) implements Handler<MqttUnsubscribeMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    @Override
    public void handle(MqttUnsubscribeMessage unsubscribe) {
        for (String t : unsubscribe.topics()) {
            log.info("Unsubscription for " + t);
            SubscribeCache.del(t, new SubscribeInfo(endpoint.clientIdentifier(), null));
        }
        endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
    }
}
