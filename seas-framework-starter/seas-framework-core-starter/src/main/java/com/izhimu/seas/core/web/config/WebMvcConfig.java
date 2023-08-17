package com.izhimu.seas.core.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.core.web.interceptor.IExpansionInterceptor;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Json配置
 *
 * @author haoran
 * @version v1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private List<IExpansionInterceptor> interceptorList;

    /**
     * ObjectMapper Bean
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonUtil.config(objectMapper);
        return objectMapper;
    }

    @Override
    public void configureMessageConverters(@Nonnull List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(objectMapper());
        converters.add(jackson2HttpMessageConverter);
    }

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        interceptorList.forEach(interceptor-> registry.addInterceptor(interceptor)
                .addPathPatterns(interceptor.addPath())
                .excludePathPatterns(interceptor.excludePath()));
    }
}
