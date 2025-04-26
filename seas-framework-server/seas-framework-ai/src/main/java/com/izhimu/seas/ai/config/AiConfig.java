package com.izhimu.seas.ai.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "seas.ai")
public class AiConfig {

    /**
     * dify api endpoint
     */
    private String difyEndpoint;
}
