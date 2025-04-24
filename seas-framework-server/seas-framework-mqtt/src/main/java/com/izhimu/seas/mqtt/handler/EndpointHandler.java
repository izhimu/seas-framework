package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.mqtt.cache.ClientCache;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 接入点处理
 *
 * @author haoran
 */
public class EndpointHandler implements Handler<MqttEndpoint> {

    @Override
    public void handle(MqttEndpoint endpoint) {
        endpoint.accept(false);
        endpoint.publishHandler(new PublishHandler(endpoint))
                // QoS 1
                .publishAcknowledgeMessageHandler(new PubAcknowledgeHandler(endpoint))
                // QoS 2
                .publishReceivedHandler(endpoint::publishRelease)
                .publishReleaseHandler(endpoint::publishComplete)
                .publishCompletionMessageHandler(new PubCompletionHandler(endpoint))
                // 订阅/取消订阅
                .subscribeHandler(new SubscribeHandler(endpoint))
                .unsubscribeHandler(new UnsubscribeHandler(endpoint))
                // 心跳
                .pingHandler(new PingHandler(endpoint))
                // 断开/关闭
                .disconnectMessageHandler(new DisconnectHandler(endpoint))
                .closeHandler(new CloseHandler(endpoint));
        log.infoT("MQTT Server", "client connect, client: {}", endpoint.clientIdentifier());
        ClientCache.put(endpoint);
    }
}
