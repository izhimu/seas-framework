package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.entity.Message;
import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.core.utils.JsonUtil;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.messages.MqttPublishMessage;

import java.nio.charset.Charset;

/**
 * 消息处理器
 *
 * @author haoran
 */
public class PublishHandler implements Handler<MqttPublishMessage> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    private final MqttEndpoint endpoint;

    public PublishHandler(MqttEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void handle(MqttPublishMessage message) {
        Message<?> object = JsonUtil.toObject(message.payload().toString(Charset.defaultCharset()), Message.class);
        log.info("{}", object);
        ack(message);
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
