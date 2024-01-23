package com.izhimu.seas.mqtt.config;

import com.izhimu.seas.core.server.ServerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 服务配置
 *
 * @author haoran
 */
@Configuration
public class ServerConfig {

    @Bean
    public ServerManager getServerManager() {
        return new ServerManager();
    }
}
