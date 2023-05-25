package com.izhimu.seas.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Seas Storage Server
 *
 * @author haoran
 */
@MapperScan({"com.izhimu.seas.storage.mapper"})
@ComponentScan(basePackages = {"com.izhimu.seas"})
@SpringBootApplication
public class SeasStorageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeasStorageServerApplication.class, args);
    }

}
