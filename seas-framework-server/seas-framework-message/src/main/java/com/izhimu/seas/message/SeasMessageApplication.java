package com.izhimu.seas.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Server
 *
 * @author haoran
 * @version v1.0
 */
@ComponentScan(basePackages = {"com.izhimu"})
@SpringBootApplication
public class SeasMessageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasMessageApplication.class, args);
    }
}
