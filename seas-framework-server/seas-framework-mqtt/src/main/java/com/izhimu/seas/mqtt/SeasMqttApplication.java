package com.izhimu.seas.mqtt;

import cn.hutool.json.JSONUtil;
import com.izhimu.seas.core.entity.Message;
import com.izhimu.seas.core.utils.JsonUtil;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.nio.charset.Charset;

/**
 * Seas Server
 *
 * @author haoran
 * @version v1.0
 */
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasMqttApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasMqttApplication.class, args);
        MqttServer mqttServer = MqttServer.create(Vertx.vertx());
        mqttServer.endpointHandler(endpoint -> {
                    // shows main connect info
                    System.out.println("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());

                    if (endpoint.auth() != null) {
                        System.out.println("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
                    }
                    System.out.println("[properties = " + endpoint.connectProperties() + "]");
//                    if (endpoint.will() != null) {
//                        System.out.println("[will topic = " + endpoint.will().getWillTopic() + " msg = " + new String(endpoint.will().getWillMessageBytes()) +
//                                " QoS = " + endpoint.will().getWillQos() + " isRetain = " + endpoint.will().isWillRetain() + "]");
//                    }
                    System.out.println("[keep alive timeout = " + endpoint.keepAliveTimeSeconds() + "]");
                    // accept connection from the remote client
                    endpoint.accept(false);

                    endpoint.publishHandler(message -> {
                                System.out.println("Just received message [" + message.payload().toString(Charset.defaultCharset()) + "] with QoS [" + message.qosLevel() + "]");

                                Message<?> object = JsonUtil.toObject(message.payload().toString(Charset.defaultCharset()), Message.class);
                                System.out.println(object);
                                if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
                                    endpoint.publishAcknowledge(message.messageId());
                                } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
                                    endpoint.publishReceived(message.messageId());
                                }
                            }).publishReleaseHandler(endpoint::publishComplete)
                            .pingHandler(v -> System.out.println("Ping received from client"));
                })
                .listen(ar -> {
                    if (ar.succeeded()) {
                        System.out.println("MQTT server is listening on port " + ar.result().actualPort());
                    } else {
                        System.out.println("Error on starting the server");
                        ar.cause().printStackTrace();
                    }
                });

    }
}
