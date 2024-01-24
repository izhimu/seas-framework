package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.mqtt.cache.SubscribeCache;
import com.izhimu.seas.mqtt.entity.SubscribeInfo;
import io.netty.handler.codec.mqtt.MqttProperties;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.MqttSubscribeMessage;
import io.vertx.mqtt.messages.codes.MqttSubAckReasonCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅处理
 *
 * @author haoran
 */
public record SubscribeHandler(MqttEndpoint endpoint) implements Handler<MqttSubscribeMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    @Override
    public void handle(MqttSubscribeMessage subscribe) {
        List<MqttSubAckReasonCode> reasonCodes = new ArrayList<>();
        for (MqttTopicSubscription s : subscribe.topicSubscriptions()) {
            log.info("Subscription for " + s.topicName() + " with QoS " + s.qualityOfService());
            SubscribeCache.put(s.topicName(), new SubscribeInfo(endpoint.clientIdentifier(), s.qualityOfService()));
            reasonCodes.add(MqttSubAckReasonCode.qosGranted(s.qualityOfService()));
        }
        endpoint.subscribeAcknowledge(subscribe.messageId(), reasonCodes, MqttProperties.NO_PROPERTIES);
    }
}
