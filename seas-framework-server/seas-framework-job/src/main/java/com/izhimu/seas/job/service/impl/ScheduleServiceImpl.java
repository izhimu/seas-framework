package com.izhimu.seas.job.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReflectUtil;
import com.izhimu.seas.core.job.AbstractJob;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.job.entity.JobTimer;
import com.izhimu.seas.job.service.ScheduleService;
import com.izhimu.seas.job.trigger.OnlyTrigger;
import com.izhimu.seas.job.trigger.RangeCronTrigger;
import com.izhimu.seas.job.trigger.RangePeriodicTrigger;
import jakarta.annotation.Resource;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 定时服务实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ScheduleServiceImpl implements ScheduleService {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final Map<String, ScheduledFuture<?>> TASK_MAP = new ConcurrentHashMap<>();

    @Resource
    private ThreadPoolTaskScheduler scheduler;

    @Override
    public boolean add(JobTimer timer) {
        if (CharSequenceUtil.isBlank(timer.getExpression()) || CharSequenceUtil.isBlank(timer.getClassPath())) {
            return false;
        }
        Trigger trigger = getTrigger(timer);
        if (Objects.isNull(trigger)) {
            return false;
        }
        AbstractJob job = getAbstractJob(timer);
        if (Objects.isNull(job)) {
            return false;
        }
        ScheduledFuture<?> schedule = scheduler.schedule(job, trigger);
        TASK_MAP.put(timer.getKey(), schedule);
        log.infoT(timer.getName(), "Added expression: {}, class: {}", timer.getExpression(), timer.getClassPath());
        return true;
    }

    @Override
    public boolean del(JobTimer timer) {
        if (CharSequenceUtil.isBlank(timer.getKey())) {
            return false;
        }
        if (!TASK_MAP.containsKey(timer.getKey())) {
            return false;
        }
        ScheduledFuture<?> scheduledFuture = TASK_MAP.get(timer.getKey());
        scheduledFuture.cancel(true);
        if (scheduledFuture.isCancelled()) {
            TASK_MAP.remove(timer.getKey());
            return true;
        }
        return false;
    }

    @Override
    public boolean run(JobTimer timer) {
        if (CharSequenceUtil.isBlank(timer.getClassPath())) {
            return false;
        }
        AbstractJob job = getAbstractJob(timer);
        if (Objects.isNull(job)) {
            return false;
        }
        scheduler.getScheduledThreadPoolExecutor().execute(job);
        return true;
    }

    @Override
    public boolean has(String key) {
        return TASK_MAP.containsKey(key);
    }

    private AbstractJob getAbstractJob(JobTimer timer) {
        Class<?> aClass;
        try {
            aClass = ClassLoaderUtil.loadClass(timer.getClassPath());
        } catch (Exception ignored) {
            return null;
        }
        if (!AbstractJob.class.isAssignableFrom(aClass)) {
            return null;
        }
        AbstractJob job = (AbstractJob) ReflectUtil.newInstance(aClass);
        job.setKey(timer.getKey());
        job.setParam(getParam(timer.getParam()));
        return job;
    }

    private Trigger getTrigger(JobTimer timer) {
        String expression = timer.getExpression();
        Integer type = timer.getType();
        if (type == 0) {
            return new RangeCronTrigger(timer.getKey(), expression, timer.getStartTime(), timer.getEndTime());
        } else if (type == 1) {
            LocalDateTime parse = LocalDateTime.parse(expression, DateTimeFormatter.ofPattern(DATE_FORMAT));
            return new OnlyTrigger(timer.getKey(), parse.atZone(ZoneId.systemDefault()).toInstant());
        } else if (type == 2) {
            TimeUnit timeUnit = null;
            long time = 0;
            if (expression.endsWith("ms")) {
                timeUnit = TimeUnit.MILLISECONDS;
                time = Long.parseLong(expression.replace("ms", ""));
            } else if (expression.endsWith("s")) {
                timeUnit = TimeUnit.SECONDS;
                time = Long.parseLong(expression.replace("s", ""));
            } else if (expression.endsWith("min")) {
                timeUnit = TimeUnit.MINUTES;
                time = Long.parseLong(expression.replace("min", ""));
            } else if (expression.endsWith("h")) {
                timeUnit = TimeUnit.HOURS;
                time = Long.parseLong(expression.replace("h", ""));
            } else if (expression.endsWith("d")) {
                timeUnit = TimeUnit.DAYS;
                time = Long.parseLong(expression.replace("d", ""));
            }
            if (Objects.isNull(timeUnit)) {
                return null;
            }
            return new RangePeriodicTrigger(timer.getKey(), time, timeUnit, timer.getStartTime(), timer.getEndTime());
        } else {
            return null;
        }
    }

    private Map<String, Object> getParam(String json) {
        if (CharSequenceUtil.isBlank(json)) {
            return Collections.emptyMap();
        }
        //noinspection unchecked
        return JsonUtil.toObject(json, Map.class);
    }
}
