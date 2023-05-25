package com.izhimu.seas.core.config;

import com.izhimu.seas.core.event.EventListenerScanHandler;
import com.izhimu.seas.core.scan.ScanHandlerHolder;
import com.izhimu.seas.core.scan.ScanServer;
import com.izhimu.seas.core.server.ServerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 默认注解扫描器配置
 *
 * @author haoran
 * @version v1.0
 */
@Configuration
public class ScanConfig {

    public static final String DEF_SCAN_PATH = "com.izhimu";

    @Bean
    public ServerManager scanServer() {
        ScanHandlerHolder.add(new EventListenerScanHandler());
        ServerManager manager = new ServerManager();
        manager.add(new ScanServer(DEF_SCAN_PATH))
                .start();
        return manager;
    }
}
