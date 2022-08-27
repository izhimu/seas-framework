package com.izhimu.seas.base;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Base Server
 *
 * @author haoran
 */
@MapperScan({"com.izhimu.seas.*.mapper"})
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasBaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasBaseServerApplication.class, args);
    }

}
