package com.izhimu.seas.healthy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Storage Server
 *
 * @author haoran
 */
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasHealthyServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasHealthyServerApplication.class, args);
    }
}
