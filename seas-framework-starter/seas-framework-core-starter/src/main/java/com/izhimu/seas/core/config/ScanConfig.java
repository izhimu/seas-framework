package com.izhimu.seas.core.config;

import com.izhimu.seas.core.scan.ScanServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * 默认注解扫描器配置
 *
 * @author haoran
 * @version v1.0
 */
@Configuration
public class ScanConfig implements ApplicationRunner {

    public static final String DEF_SCAN_PATH = "com.izhimu";

    @Override
    public void run(ApplicationArguments args) {
        new ScanServer(DEF_SCAN_PATH).run();
    }
}
