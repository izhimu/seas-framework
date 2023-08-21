package com.izhimu.seas.core.web.config;

import com.izhimu.seas.core.web.interceptor.IExpansionInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * 默认拦截器注入
 *
 * @author haoran
 * @version v1.0
 */
@Configuration
public class DefInterceptorConfig {

    @Bean
    @ConditionalOnMissingBean(IExpansionInterceptor.class)
    public List<IExpansionInterceptor> interceptorList() {
        return Collections.emptyList();
    }
}
