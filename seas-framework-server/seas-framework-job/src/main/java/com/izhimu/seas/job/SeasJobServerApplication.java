package com.izhimu.seas.job;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Job Server
 *
 * @author haoran
 */
@MapperScan({"com.izhimu.seas.job.mapper"})
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasJobServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasJobServerApplication.class, args);
    }

}
