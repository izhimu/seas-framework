package com.izhimu.seas.job.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEventListener;
import com.izhimu.seas.job.entity.JobTimer;
import com.izhimu.seas.job.event.JobEvent;
import com.izhimu.seas.job.service.JobTimerService;

import java.util.Objects;

/**
 * 任务完成监听器
 *
 * @author haoran
 * @version v1.0
 */
@EventListener(value = JobEvent.E_JOB_FINISH, async = true)
public class JobFinishListener implements IEventListener {

    private final JobTimerService timerService;

    public JobFinishListener() {
        timerService = SpringUtil.getBean(JobTimerService.class);
    }

    @Override
    public boolean onEvent(Object data) {
        if (Objects.isNull(data)) {
            return false;
        }
        return timerService.lambdaUpdate()
                .eq(JobTimer::getKey, data)
                .set(JobTimer::getStatus, 2)
                .update();
    }
}
