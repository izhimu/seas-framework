package com.izhimu.seas.mqtt;

import com.izhimu.seas.cache.service.SetCacheService;
import com.izhimu.seas.core.server.ServerManager;
import com.izhimu.seas.mqtt.cache.MQTTCache;
import com.izhimu.seas.mqtt.config.MQTTConfig;
import com.izhimu.seas.mqtt.server.MQTTServer;
import jakarta.annotation.Resource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Server
 *
 * @author haoran
 * @version v1.0
 */
@MapperScan({"com.izhimu.seas.mqtt.mapper"})
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasMqttApplication implements ApplicationRunner, DisposableBean {

    public static void main(String[] args) {
        SpringApplication.run(SeasMqttApplication.class, args);
    }

    @Resource
    private SetCacheService setCacheService;

    @Resource
    private ServerManager serverManager;

    @Resource
    private MQTTConfig mqttConfig;

    @Override
    public void run(ApplicationArguments args) {
        MQTTCache.init(setCacheService);
        serverManager
                .add(new MQTTServer(mqttConfig))
                .start();
    }

    @Override
    public void destroy() {
        serverManager.stop();
    }
}
