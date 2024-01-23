package com.izhimu.seas.mqtt.server;

import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.core.server.IServer;
import com.izhimu.seas.mqtt.config.MQTTConfig;
import com.izhimu.seas.mqtt.handler.EndpointHandler;
import io.vertx.core.*;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;

import java.util.Map;

/**
 * MQTT服务端
 *
 * @author haoran
 */

public class MQTTServer implements IServer {

    private static final LogWrapper log = LogWrapper.build("MQTTServer");

    private final MQTTConfig config;

    private MqttServer server;

    public MQTTServer(MQTTConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        server(Vertx.vertx());
    }

    @Override
    public void shutdown() {
        server.close(v -> log.info("Close"));
    }

    private void server(Vertx vertx) {
        MqttServerOptions options = new MqttServerOptions();
        options.setPort(config.getPort());
        server = MqttServer.create(vertx, options)
                .endpointHandler(new EndpointHandler())
                .listen(ar -> {
                    if (ar.succeeded()) {
                        log.info(Map.of("port", config.getPort()), "Start success");
                    } else {
                        log.error(ar.cause());
                    }
                });
    }
}
