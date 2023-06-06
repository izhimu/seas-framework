package com.izhimu.seas.core.config;

import com.izhimu.seas.core.event.EventListenerScanHandler;
import com.izhimu.seas.core.scan.ScanHandlerHolder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 事件扫描配置
 *
 * @author haoran
 * @version v1.0
 */
@Order(10000)
@Configuration
public class EventScanConfig implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        ScanHandlerHolder.add(new EventListenerScanHandler());
    }
}
