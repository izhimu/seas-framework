package com.izhimu.seas.core.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izhimu.seas.core.utils.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Json配置
 *
 * @author haoran
 * @version v1.0
 */
@Configuration
public class JsonConfig {

    /**
     * ObjectMapper Bean
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonUtil.config(objectMapper);
        return objectMapper;
    }
}
