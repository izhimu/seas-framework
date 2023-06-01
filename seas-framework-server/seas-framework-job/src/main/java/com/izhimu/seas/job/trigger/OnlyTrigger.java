package com.izhimu.seas.job.trigger;

import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.job.event.JobEvent;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;
import java.util.Objects;

/**
 * @author haoran
 * @version v1.0
 */
public class OnlyTrigger implements Trigger {

    private final String key;
    private final Date next;

    public OnlyTrigger(String key, Date next) {
        this.key = key;
        this.next = next;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        Date date = triggerContext.lastActualExecutionTime();
        if (Objects.nonNull(date)) {
            EventManager.trigger(JobEvent.E_JOB_FINISH, key);
            return null;
        }
        return next;
    }
}
