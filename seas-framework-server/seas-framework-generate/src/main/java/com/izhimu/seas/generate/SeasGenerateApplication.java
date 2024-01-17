package com.izhimu.seas.generate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Server
 *
 * @author haoran
 * @version v1.0
 */
@MapperScan({"com.izhimu.seas.generate.mapper"})
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasGenerateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasGenerateApplication.class, args);
    }
}
