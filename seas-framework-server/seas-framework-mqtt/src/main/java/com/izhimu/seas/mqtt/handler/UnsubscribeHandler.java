package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.mqtt.cache.SubscribeCache;
import com.izhimu.seas.mqtt.entity.SubscribeInfo;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttUnsubscribeMessage;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 退订处理
 *
 * @author haoran
 */
public record UnsubscribeHandler(MqttEndpoint endpoint) implements Handler<MqttUnsubscribeMessage> {

    @Override
    public void handle(MqttUnsubscribeMessage unsubscribe) {
        for (String t : unsubscribe.topics()) {
            log.infoT("MQTT Server", "unsubscription for {}, client: {}", t, endpoint.clientIdentifier());
            SubscribeCache.del(t, new SubscribeInfo(endpoint.clientIdentifier(), null));
        }
        endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
    }
}
