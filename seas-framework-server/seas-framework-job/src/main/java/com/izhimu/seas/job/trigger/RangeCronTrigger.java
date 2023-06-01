package com.izhimu.seas.job.trigger;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.job.event.JobEvent;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author haoran
 * @version v1.0
 */
public class RangeCronTrigger extends CronTrigger {

    private final String key;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public RangeCronTrigger(String key, String expression, LocalDateTime startTime, LocalDateTime endTime) {
        super(expression);
        this.key = key;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public Date nextExecutionTime(@NonNull TriggerContext triggerContext) {
        Date date = super.nextExecutionTime(triggerContext);
        if (Objects.isNull(date)) {
            return null;
        }
        // 晚于结束时间
        if (Objects.nonNull(endTime) && LocalDateTimeUtil.toEpochMilli(endTime) < date.getTime()) {
            EventManager.trigger(JobEvent.E_JOB_FINISH, key);
            return null;
        }
        // 早于开始时间
        if (Objects.nonNull(startTime) && LocalDateTimeUtil.toEpochMilli(startTime) > date.getTime()) {
            return DateUtil.date(CronExpression.parse(super.getExpression()).next(startTime));
        }
        return date;
    }
}
