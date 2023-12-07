package com.izhimu.seas.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring上下文工具配置
 *
 * @author haoran
 */
@Configuration
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class SpringContextConfig {
}
