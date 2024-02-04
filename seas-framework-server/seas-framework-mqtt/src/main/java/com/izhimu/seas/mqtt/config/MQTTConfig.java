package com.izhimu.seas.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * MQTT服务配置
 *
 * @author haoran
 */
@Data
@Configuration
@ConfigurationProperties("seas.mqtt")
public class MQTTConfig {

    /**
     * 端口
     */
    private int port = 1883;
    /**
     * 启用Websocket
     */
    private boolean wsEnable = false;
    /**
     * Websocket 端口
     */
    private int wsPort = 8080;
}
