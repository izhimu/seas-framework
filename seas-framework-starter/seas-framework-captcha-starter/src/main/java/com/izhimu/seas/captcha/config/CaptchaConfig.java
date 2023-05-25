package com.izhimu.seas.captcha.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置类
 *
 * @author haoran
 * @version v1.0
 */
@Data
@Configuration
@ConfigurationProperties("seas.captcha")
public class CaptchaConfig {

    /**
     * 校验滑动拼图允许误差偏移量
     * px
     */
    private Integer slipOffset = 5;
    /**
     * 滑块干扰项
     * 0|1|2
     */
    private Integer interferenceOptions = 0;
    /**
     * 验证码过期时间
     * 秒
     */
    private Long captchaExpire = 120L;
    /**
     * 回执过期时间
     * 秒
     */
    private Long receiptExpire = 120L;
    /**
     * 原始图片路径
     */
    private String originalPath = "classpath:images/original/";
    /**
     * 原始图片数量
     */
    private Integer originalSize = 32;
    /**
     * 块图片路径
     */
    private String blockPath = "classpath:images/block/";
    /**
     * 块图片数量
     */
    private Integer blockSize = 4;
}
