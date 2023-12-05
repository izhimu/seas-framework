package com.izhimu.seas.job.trigger;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.job.event.JobEvent;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 有时间范围的周期性触发器
 *
 * @author haoran
 * @version v1.0
 */
public class RangePeriodicTrigger extends PeriodicTrigger {

    private final String key;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public RangePeriodicTrigger(String key, long period, TimeUnit timeUnit, LocalDateTime startTime, LocalDateTime endTime) {
        super(Duration.of(period, timeUnit.toChronoUnit()));
        this.key = key;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public Instant nextExecution(@NonNull TriggerContext triggerContext) {
        Instant instant = super.nextExecution(triggerContext);
        if (Objects.isNull(instant)) {
            return null;
        }
        // 晚于结束时间
        if (Objects.nonNull(endTime) && LocalDateTimeUtil.toEpochMilli(endTime) < instant.toEpochMilli()) {
            EventManager.trigger(JobEvent.E_JOB_FINISH, key);
            return null;
        }
        // 早于开始时间
        if (Objects.nonNull(startTime) && LocalDateTimeUtil.toEpochMilli(startTime) > instant.toEpochMilli()) {
            return startTime.atZone(ZoneId.systemDefault()).toInstant();
        }
        return instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RangePeriodicTrigger that = (RangePeriodicTrigger) o;
        return Objects.equals(key, that.key) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), key, startTime, endTime);
    }
}
