package com.izhimu.seas.base.config;

import com.izhimu.seas.base.constant.SecurityConstant;
import com.izhimu.seas.base.entity.EncryptKey;
import com.izhimu.seas.base.filter.CustomAuthenticationFilter;
import com.izhimu.seas.base.handler.*;
import com.izhimu.seas.base.holder.LoginHolder;
import com.izhimu.seas.base.provider.CustomAuthenticationProvider;
import com.izhimu.seas.base.resolver.DiverseHttpSessionIdResolver;
import com.izhimu.seas.base.service.EncryptService;
import com.izhimu.seas.base.service.SecurityService;
import com.izhimu.seas.base.service.SysAccountLogService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@ConfigurationProperties("security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 超级管理员列表
     */
    @Getter
    @Setter
    private List<String> supers = new ArrayList<>();

    @Resource
    private SecurityService securityService;

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    @Resource
    private SysAccountLogService accountLogService;

    @Resource
    private LoginHolder loginHolder;

    @Resource
    private LoginConfig loginConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Filter authenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(encryptService, loginHolder, loginConfig);
        filter.setAuthenticationSuccessHandler(new CustomLoginSuccessHandler(accountLogService, loginHolder));
        filter.setAuthenticationFailureHandler(new CustomLoginFailureHandler(accountLogService, loginHolder, loginConfig));
        filter.setAuthenticationManager(authenticationManagerBean());
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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 拦截范围
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .antMatchers("/security/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        // 自定义过滤器
        http.addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());

        // 退出配置
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(SecurityConstant.URL_LOGOUT, "POST"))
                .deleteCookies()
                .logoutSuccessHandler(new CustomLogoutSuccessHandler(accountLogService));

        // 错误处理
        http.exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomNoLoginHandler());

        http.cors();
        http.csrf().disable();
    }
}
