package com.izhimu.seas.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 日志配置
 *
 * @author haoran
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties("seas.log")
public class LogConfig {

    /**
     * 日志保留时间
     * 天
     */
    private int retainTime = 30;
    /**
     * 登录日志保留时间
     * 天
     */
    private int loginRetainTime = 30;
}
