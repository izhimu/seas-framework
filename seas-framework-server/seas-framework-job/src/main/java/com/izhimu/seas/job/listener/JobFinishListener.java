package com.izhimu.seas.job.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEvent;
import com.izhimu.seas.core.event.IEventListener;
import com.izhimu.seas.job.entity.SysTimer;
import com.izhimu.seas.job.event.JobEvent;
import com.izhimu.seas.job.service.SysTimerService;

import java.util.Objects;

/**
 * 任务完成监听器
 *
 * @author haoran
 * @version v1.0
 */
@EventListener
public class JobFinishListener implements IEventListener {

    private final SysTimerService timerService;

    public JobFinishListener() {
        timerService = SpringUtil.getBean(SysTimerService.class);
    }

    @Override
    public boolean onEvent(Object data) {
        if (Objects.isNull(data)) {
            return false;
        }
        return timerService.lambdaUpdate()
                .eq(SysTimer::getKey, data)
                .set(SysTimer::getStatus, 2)
                .update();
    }

    @Override
    public IEvent getEvent() {
        return JobEvent.E_JOB_FINISH;
    }

    @Override
    public boolean async() {
        return true;
    }
}
