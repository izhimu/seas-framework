package com.izhimu.seas.job.listener;

import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEvent;
import com.izhimu.seas.core.event.IEventListener;
import com.izhimu.seas.job.event.JobEvent;

/**
 * 任务完成监听器
 *
 * @author haoran
 * @version v1.0
 */
@EventListener
public class JobFinishListener implements IEventListener {
    @Override
    public boolean onEvent(Object data) {
        System.out.println(data);
        return true;
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
