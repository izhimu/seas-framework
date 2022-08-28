package com.izhimu.seas.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 登录配置
 *
 * @author haoran
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties("login")
public class LoginConfig {

    /**
     * 错误重试时间（秒）
     */
    private Long errTime = 300L;
    /**
     * 错误次数限制
     */
    private Integer errNum = 5;
}
