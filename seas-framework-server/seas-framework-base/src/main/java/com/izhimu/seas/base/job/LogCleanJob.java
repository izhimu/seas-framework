package com.izhimu.seas.base.job;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.BasLogService;
import com.izhimu.seas.core.annotation.JobRegister;
import com.izhimu.seas.core.enums.TimerType;
import com.izhimu.seas.core.job.AbstractJob;

import java.util.Map;

/**
 * 日志清理任务
 *
 * @author haoran
 * @version v1.0
 */
@JobRegister(key = "BASE.LOG_CLEAN_JOB", name = "日志清理任务", type = TimerType.CRON, expression = "0 0 3 1/1 * ?")
public class LogCleanJob extends AbstractJob {

    private final BasLogService logService;

    public LogCleanJob() {
        this.logService = SpringUtil.getBean(BasLogService.class);
    }

    @Override
    public void run(Map<String, Object> param) {
        logService.cleanLog();
    }
}
