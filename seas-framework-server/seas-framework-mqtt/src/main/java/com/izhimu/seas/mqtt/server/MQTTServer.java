package com.izhimu.seas.mqtt.server;

import com.izhimu.seas.core.server.IServer;
import com.izhimu.seas.mqtt.config.MQTTConfig;
import com.izhimu.seas.mqtt.handler.EndpointHandler;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * MQTT服务端
 *
 * @author haoran
 */

public class MQTTServer implements IServer {

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
        server.close(v -> log.info("[MQTT Server] close"));
    }

    private void server(Vertx vertx) {
        MqttServerOptions options = new MqttServerOptions();
        options.setPort(config.getPort());
        server = MqttServer.create(vertx, options)
                .endpointHandler(new EndpointHandler())
                .listen(ar -> {
                    if (ar.succeeded()) {
                        log.info("[MQTT Server] start success, port: {}", config.getPort());
                    } else {
                        log.error(ar.cause());
                    }
                });
    }
}
