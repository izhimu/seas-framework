package com.izhimu.seas.security.config;

import cn.dev33.satoken.config.SaTokenConfig;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;

import static com.izhimu.seas.security.constant.SecurityConstant.TOKEN_NAME;

/**
 * Http安全配置
 *
 * @author haoran
 * @version v1.0
 */
@Configuration
public class HttpSecurityConfig {

    @Resource
    private SecurityConfig securityConfig;

    @Resource
    public void configSaToken(SaTokenConfig config) {
        config.setTokenName(TOKEN_NAME);
        config.setActiveTimeout(securityConfig.getTokenTime());
        config.setIsShare(false);
        config.setIsPrint(false);
        config.setTokenStyle("tik");
        config.setIsReadCookie(false);
    }
}
