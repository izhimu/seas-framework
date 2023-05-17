package com.izhimu.seas.security.config;

import com.anji.captcha.service.CaptchaService;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.security.entity.EncryptKey;
import com.izhimu.seas.security.filter.CustomAuthenticationFilter;
import com.izhimu.seas.security.handler.*;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.security.provider.CustomAuthenticationProvider;
import com.izhimu.seas.security.resolver.DiverseHttpSessionIdResolver;
import com.izhimu.seas.security.service.EncryptService;
import com.izhimu.seas.security.service.SecurityService;
import com.izhimu.seas.security.service.impl.DefSecurityServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * 安全配置
 *
 * @author haoran
 */
@Configuration
@ConfigurationProperties("seas.security")
public class SecurityConfig {

    /**
     * 超级管理员列表
     */
    @Getter
    @Setter
    private List<String> supers = new ArrayList<>();

    @Lazy
    @Resource
    private SecurityService securityService;

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    @Resource
    private CaptchaService captchaService;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private LoginHolder loginHolder;

    @Resource
    private LoginConfig loginConfig;

    @Bean
    @ConditionalOnMissingBean(SecurityService.class)
    public SecurityService defSecurityService() {
        return new DefSecurityServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Filter authenticationFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(encryptService, captchaService, loginHolder, loginConfig);
        filter.setAuthenticationSuccessHandler(new CustomLoginSuccessHandler(applicationContext, loginHolder));
        filter.setAuthenticationFailureHandler(new CustomLoginFailureHandler(applicationContext, loginHolder, loginConfig));
        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        return filter;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder(), securityService, supers);
    }

    @Bean
    public HttpSessionIdResolver sessionIdResolver() {
        return new DiverseHttpSessionIdResolver();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 拦截范围
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers("/security/**", "/captcha/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        // 自定义过滤器
        http.addFilterAt(authenticationFilter(authenticationConfiguration), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());

        // 退出配置
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(SecurityConstant.URL_LOGOUT, "POST"))
                .deleteCookies()
                .logoutSuccessHandler(new CustomLogoutSuccessHandler(applicationContext));

        // 错误处理
        http.exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomNoLoginHandler());

        http.cors();
        http.csrf().disable();
        return http.build();
    }
}
