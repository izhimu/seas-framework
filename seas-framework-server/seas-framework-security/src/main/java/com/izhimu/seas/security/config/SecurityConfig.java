package com.izhimu.seas.security.config;

import com.izhimu.seas.core.enums.DeviceType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录配置
 *
 * @author haoran
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties("seas.security")
public class SecurityConfig {

    /**
     * token有效期（秒）
     */
    private Long tokenTimeout = 2592000L;
    /**
     * active有效期（秒）
     */
    private Map<DeviceType, Long> activeTimeout = new HashMap<>();
    /**
     * 错误重试时间（秒）
     */
    private Long errTime = 300L;
    /**
     * 错误次数限制
     */
    private Integer errNum = 5;
    /**
     * 超级管理员列表
     */
    private List<String> supers = new ArrayList<>();
    /**
     * 排除路径
     */
    private List<String> excludePath = new ArrayList<>();
}
