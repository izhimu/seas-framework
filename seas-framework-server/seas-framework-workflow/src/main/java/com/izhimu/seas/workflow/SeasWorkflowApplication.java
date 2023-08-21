package com.izhimu.seas.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Server
 *
 * @author haoran
 * @version v1.0
 */
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasWorkflowApplication.class, args);
    }
}
