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
        log.infoT(endpoint.clientIdentifier(), "client connect");
        ClientCache.put(endpoint);
        endpoint.pingHandler(new PingHandler(endpoint))
                .disconnectMessageHandler(new DisconnectHandler(endpoint))
                .subscribeHandler(new SubscribeHandler(endpoint))
                .unsubscribeHandler(new UnsubscribeHandler(endpoint))
                .publishHandler(new PublishHandler(endpoint))
                .publishReleaseHandler(endpoint::publishComplete)
                .publishReceivedHandler(endpoint::publishRelease)
                .publishCompletionMessageHandler(new PubCompletionHandler(endpoint))
                .publishAcknowledgeMessageHandler(new PubAcknowledgeHandler(endpoint));
    }
}
