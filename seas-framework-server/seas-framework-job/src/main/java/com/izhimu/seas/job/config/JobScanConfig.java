package com.izhimu.seas.job.config;

import com.izhimu.seas.core.scan.ScanHandlerHolder;
import com.izhimu.seas.job.handler.JobRegisterScanHandler;
import com.izhimu.seas.job.service.SysTimerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

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
    private SysTimerService timerService;

    @Override
    public void run(ApplicationArguments args) {
        ScanHandlerHolder.add(new JobRegisterScanHandler(timerService));
    }
}
