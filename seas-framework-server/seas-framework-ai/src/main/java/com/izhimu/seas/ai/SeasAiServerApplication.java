package com.izhimu.seas.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Seas AI Server
 *
 * @author haoran
 */
@EnableAsync
@MapperScan({"com.izhimu.seas.ai.mapper"})
@ComponentScan(basePackages = {"com.izhimu"})
@SpringBootApplication
public class SeasAiServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasAiServerApplication.class, args);
    }
}