package com.izhimu.seas.security.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.stp.StpUtil;
import com.izhimu.seas.core.web.Result;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.izhimu.seas.security.constant.SecurityConstant.TOKEN_NAME;

/**
 * SaToken配置
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
        config.setTimeout(securityConfig.getTokenTime());
        config.setIsShare(false);
        config.setIsPrint(false);
        config.setTokenStyle("tik");
    }

    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")
                .addExclude("/security/**", "/captcha/**")
                .setAuth(obj -> StpUtil.checkLogin())
                .setError(e -> {
                    SaHolder.getResponse().setHeader("Content-Type", "application/json");
                    return Result.fail(e.getMessage());
                });
    }
}
