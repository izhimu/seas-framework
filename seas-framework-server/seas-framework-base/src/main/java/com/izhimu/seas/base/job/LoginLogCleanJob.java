package com.izhimu.seas.base.job;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.BasAccountLogService;
import com.izhimu.seas.core.annotation.JobRegister;
import com.izhimu.seas.core.enums.TimerType;
import com.izhimu.seas.core.job.AbstractJob;

import java.util.Map;

/**
 * 登录日志清理任务
 *
 * @author haoran
 * @version v1.0
 */
@JobRegister(key = "BASE.LOGIN_LOG_CLEAN_JOB", name = "登录日志清理任务", type = TimerType.CRON, expression = "0 30 3 1/1 * ?")
public class LoginLogCleanJob extends AbstractJob {

    private final BasAccountLogService accountLogService;

    public LoginLogCleanJob() {
        this.accountLogService = SpringUtil.getBean(BasAccountLogService.class);
    }

    @Override
    public void run(Map<String, Object> param) {
        accountLogService.cleanLog();
    }
}
