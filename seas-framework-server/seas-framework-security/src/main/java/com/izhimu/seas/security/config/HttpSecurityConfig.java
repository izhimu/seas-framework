package com.izhimu.seas.security.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.dao.SaTokenDao;
import com.izhimu.seas.cache.service.CacheService;
import com.izhimu.seas.security.service.impl.TokenCacheServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    private CacheService cacheService;

    @Primary
    @Bean
    public SaTokenDao saTokenDao() {
        return new TokenCacheServiceImpl(cacheService);
    }

    @Resource
    public void configSaToken(SaTokenConfig config) {
        config.setTokenName(TOKEN_NAME);
        config.setTimeout(securityConfig.getTokenTime());
        config.setIsShare(false);
        config.setIsPrint(false);
        config.setTokenStyle("tik");
        config.setIsReadCookie(false);
    }
}
