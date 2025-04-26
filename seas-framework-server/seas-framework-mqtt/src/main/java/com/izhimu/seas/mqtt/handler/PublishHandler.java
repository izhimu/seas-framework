package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.mqtt.cache.ClientCache;
import com.izhimu.seas.mqtt.cache.SubscribeCache;
import com.izhimu.seas.mqtt.cache.TopicCache;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPublishMessage;

import java.nio.charset.Charset;
import java.util.Objects;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 消息处理器
 *
 * @author haoran
 */
public record PublishHandler(MqttEndpoint endpoint) implements Handler<MqttPublishMessage> {

    @Override
    public void handle(MqttPublishMessage message) {
        log.debugT("MQTT Server", "publish message, client: {}, msg: {}", endpoint.clientIdentifier(), message.payload().toString(Charset.defaultCharset()));
        TopicCache.putRule(message.topicName());
        publish(message);
        ack(message);
    }

    private void publish(MqttPublishMessage message) {
        TopicCache.getRule(message.topicName()).forEach(rule ->
                SubscribeCache.get(rule).forEach(subscribe -> {
                    MqttEndpoint client = ClientCache.get(subscribe.clientId());
                    if (Objects.nonNull(client)) {
                        if (client.isConnected()) {
                            client.publish(message.topicName(), message.payload(), qosAdaptation(message.qosLevel(), subscribe.qos()), message.isDup(), message.isRetain());
                        } else {
                            ClientCache.del(client.clientIdentifier());
                        }
                    }
                }));
    }

    /**
     * 回复信息
     *
     * @param message MqttPublishMessage
     */
    private void ack(MqttPublishMessage message) {
        if (Objects.equals(MqttQoS.AT_LEAST_ONCE, message.qosLevel())) {
            endpoint.publishAcknowledge(message.messageId());
        } else if (Objects.equals(MqttQoS.EXACTLY_ONCE, message.qosLevel())) {
            endpoint.publishReceived(message.messageId());
        }
    }

    /**
     * QoS适配
     *
     * @param source 源QoS
     * @param target 目标QoS
     */
    private MqttQoS qosAdaptation(MqttQoS source, MqttQoS target) {
        if (source.value() > target.value()) {
            return target;
        } else {
            return source;
        }
    }
}
