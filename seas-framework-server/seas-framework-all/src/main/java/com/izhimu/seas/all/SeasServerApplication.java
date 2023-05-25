package com.izhimu.seas.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Server
 *
 * @author haoran
 */
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasServerApplication.class, args);
    }
}
