package com.izhimu.seas.job;

import com.izhimu.seas.job.service.JobTimerService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author haoran
 * @version v1.0
 */
@Component
public class ApplicationStarted implements ApplicationRunner {

    @Resource
    private JobTimerService timerService;

    @Override
    public void run(ApplicationArguments args) {
        timerService.initSchedule();
    }
}
