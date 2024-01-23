package com.izhimu.seas.mqtt.handler;

import com.izhimu.seas.core.log.LogWrapper;
import io.vertx.core.Handler;
import io.vertx.mqtt.MqttEndpoint;

/**
 * 接入点处理
 *
 * @author haoran
 */
public class EndpointHandler implements Handler<MqttEndpoint> {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    @Override
    public void handle(MqttEndpoint endpoint) {
        log.info("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());
        if (endpoint.auth() != null) {
            log.info("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
        }
        endpoint.accept(false);
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
