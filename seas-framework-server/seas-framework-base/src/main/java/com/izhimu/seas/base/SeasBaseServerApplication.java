package com.izhimu.seas.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Seas Base Server
 *
 * @author haoran
 */
@EnableAsync
@MapperScan({"com.izhimu.seas.base.mapper"})
@ComponentScan(basePackages = {"com.izhimu"})
@SpringBootApplication
public class SeasBaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasBaseServerApplication.class, args);
    }
}