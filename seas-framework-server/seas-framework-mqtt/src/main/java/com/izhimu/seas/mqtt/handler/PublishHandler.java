package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.mqtt.cache.ClientCache;
import com.izhimu.seas.mqtt.cache.TopicCache;
import com.izhimu.seas.mqtt.cache.SubscribeCache;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPublishMessage;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 消息处理器
 *
 * @author haoran
 */
public record PublishHandler(MqttEndpoint endpoint) implements Handler<MqttPublishMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    @Override
    public void handle(MqttPublishMessage message) {
        log.info("{}", message.payload().toString(Charset.defaultCharset()));
        TopicCache.putRule(message.topicName());
        publish(message);
        ack(message);
    }

    private void publish(MqttPublishMessage message) {
        TopicCache.getRule(message.topicName()).forEach(rule ->
                SubscribeCache.get(rule).forEach(subscribe -> {
                    MqttEndpoint client = ClientCache.get(subscribe.clientId());
                    if (Objects.nonNull(client)) {
                        client.publish(message.topicName(), message.payload(), subscribe.qos(), message.isDup(), message.isRetain());
                    }
                }));
    }

    /**
     * 回复信息
     *
     * @param message MqttPublishMessage
     */
    private void ack(MqttPublishMessage message) {
        if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
            endpoint.publishAcknowledge(message.messageId());
        } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
            endpoint.publishReceived(message.messageId());
        }
    }
}
