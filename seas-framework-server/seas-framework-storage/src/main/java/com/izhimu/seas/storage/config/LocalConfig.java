package com.izhimu.seas.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 本地文件配置
 * @author haoran
 */
@Data
@Configuration
@ConfigurationProperties("seas.storage.local")
public class LocalConfig {

    private String path;
}
