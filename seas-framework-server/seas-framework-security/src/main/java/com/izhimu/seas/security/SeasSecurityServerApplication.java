package com.izhimu.seas.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Base Server
 *
 * @author haoran
 */
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasSecurityServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasSecurityServerApplication.class, args);
    }

}
