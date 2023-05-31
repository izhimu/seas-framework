package com.izhimu.seas.job.jobs;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class TestJob extends AbstractJob {
    @Override
    public void run(Map<String, Object> param) {
        log.info(LocalDateTime.now().toString());
    }
}
