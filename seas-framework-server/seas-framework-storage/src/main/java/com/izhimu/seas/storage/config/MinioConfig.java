package com.izhimu.seas.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Minio配置
 *
 * @author haoran
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties("seas.minio")
public class MinioConfig {

    /**
     * 服务URL
     */
    private String endPoint;
    /**
     * 账户
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 存储桶
     */
    private String bucket;
    /**
     * 代理地址
     */
    private String proxyHost;
}
