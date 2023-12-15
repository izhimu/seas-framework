package com.izhimu.seas.base.job;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.BasAccountLogService;
import com.izhimu.seas.core.annotation.JobRegister;
import com.izhimu.seas.core.enums.TimerType;
import com.izhimu.seas.core.job.AbstractJob;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LoginLogCleanJob that = (LoginLogCleanJob) o;
        return Objects.equals(accountLogService, that.accountLogService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountLogService);
    }
}
