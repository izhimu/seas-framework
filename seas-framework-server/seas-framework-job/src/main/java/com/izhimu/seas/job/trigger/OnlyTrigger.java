package com.izhimu.seas.job.trigger;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.util.Date;
import java.util.Objects;

/**
 * @author haoran
 * @version v1.0
 */
public class OnlyTrigger implements Trigger {

    private final Date next;

    public OnlyTrigger(Date next) {
        this.next = next;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        Date date = triggerContext.lastActualExecutionTime();
        if (Objects.nonNull(date)) {
            return null;
        }
        return next;
    }
}
