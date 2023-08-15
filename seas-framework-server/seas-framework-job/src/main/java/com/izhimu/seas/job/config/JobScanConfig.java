package com.izhimu.seas.job.config;

import com.izhimu.seas.core.scan.ScanHandlerHolder;
import com.izhimu.seas.job.handler.JobRegisterScanHandler;
import com.izhimu.seas.job.service.JobTimerService;
import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 任务扫描配置
 *
 * @author haoran
 * @version v1.0
 */
@Order(10000)
@Configuration
public class JobScanConfig implements ApplicationRunner {

    @Resource
    private JobTimerService timerService;

    @Override
    public void run(ApplicationArguments args) {
        ScanHandlerHolder.add(new JobRegisterScanHandler(timerService));
    }
}
