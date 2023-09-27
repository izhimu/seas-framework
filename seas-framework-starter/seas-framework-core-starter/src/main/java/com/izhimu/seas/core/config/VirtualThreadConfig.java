package com.izhimu.seas.core.config;

import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.Executors;

/**
 * 线程池配置
 *
 * @author haoran
 * @version v1.0
 */
@Configuration
public class VirtualThreadConfig {

    @Bean
    public AsyncTaskExecutor applicationTaskExecutor() {
        return new TaskExecutorAdapter(Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual().name("V#Async-", 1).factory()
        ));
    }

    @Bean
    public UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer() {
        return deploymentInfo -> deploymentInfo.setExecutor(Executors.newThreadPerTaskExecutor(
                Thread.ofVirtual().name("V#Undertow-", 1).factory()
        ));
    }
}
