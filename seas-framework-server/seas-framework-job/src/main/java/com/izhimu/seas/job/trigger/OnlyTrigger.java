package com.izhimu.seas.job.trigger;

import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.job.event.JobEvent;
import jakarta.annotation.Nonnull;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import java.time.Instant;
import java.util.Objects;

/**
 * 单次触发器
 *
 * @author haoran
 * @version v1.0
 */
public class OnlyTrigger implements Trigger {

    private final String key;
    private final Instant next;

    public OnlyTrigger(String key, Instant next) {
        this.key = key;
        this.next = next;
    }

    @Override
    public Instant nextExecution(@Nonnull TriggerContext triggerContext) {
        Instant instant = triggerContext.lastActualExecution();
        if (Objects.nonNull(instant)) {
            EventManager.trigger(JobEvent.E_JOB_FINISH, key);
            return null;
        }
        return next;
    }
}
