package com.izhimu.seas.job.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.common.utils.JsonUtil;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.job.entity.SysTimer;
import com.izhimu.seas.job.jobs.AbstractJob;
import com.izhimu.seas.job.mapper.SysTimerMapper;
import com.izhimu.seas.job.service.SysTimerService;
import com.izhimu.seas.job.trigger.OnlyTrigger;
import com.izhimu.seas.job.trigger.RangeCronTrigger;
import com.izhimu.seas.job.trigger.RangePeriodicTrigger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 定时器服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
public class SysTimerServiceImpl extends BaseServiceImpl<SysTimerMapper, SysTimer> implements SysTimerService {

    private static final Map<String, ScheduledFuture<?>> TASK_MAP = new ConcurrentHashMap<>();

    @Resource
    private ThreadPoolTaskScheduler scheduler;

    @Override
    public Long add(SysTimer entity) {
        if (Objects.isNull(entity.getStatus())) {
            entity.setStatus(0);
        } else if (entity.getStatus() == 1) {
            addSchedule(entity);
        }
        return super.add(entity);
    }

    @Override
    public boolean updateById(SysTimer entity) {
        super.updateById(entity);
        SysTimer timer = this.getById(entity.getId());
        if (TASK_MAP.containsKey(timer.getKey())) {
            removeSchedule(timer);
        }
        if (entity.getStatus() == 1) {
            addSchedule(timer);
        }
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        SysTimer timer = this.getById(id);
        if (TASK_MAP.containsKey(timer.getKey())) {
            removeSchedule(timer);
        }
        return super.removeById(id);
    }

    @Override
    public boolean initSchedule() {
        log.info("加载定时任务 => 开始");
        int count = 0;
        List<SysTimer> list = this.lambdaQuery()
                .eq(SysTimer::getStatus, 1)
                .list();
        for (SysTimer timer : list) {
            if (StrUtil.isBlank(timer.getExpression()) || StrUtil.isBlank(timer.getClassPath())) {
                continue;
            }
            Trigger trigger = getTrigger(timer);
            if (Objects.isNull(trigger)) {
                continue;
            }
            Class<?> aClass = ClassLoaderUtil.loadClass(timer.getClassPath());
            if (!AbstractJob.class.isAssignableFrom(aClass)) {
                continue;
            }
            AbstractJob job = (AbstractJob) ReflectUtil.newInstance(aClass);
            job.setKey(timer.getKey());
            job.setParam(getParam(timer.getParam()));
            ScheduledFuture<?> schedule = scheduler.schedule(job, trigger);
            TASK_MAP.put(timer.getKey(), schedule);
            count++;
            log.info("[{}]任务 => expression: {}, class: {} 加载完成", timer.getName(), timer.getExpression(), timer.getClassPath());
        }
        log.info("加载定时任务 <= 结束 | 任务数：{}", count);
        return true;
    }

    @Override
    public boolean addSchedule(SysTimer timer) {
        if (StrUtil.isBlank(timer.getExpression()) || StrUtil.isBlank(timer.getClassPath())) {
            return false;
        }
        Trigger trigger = getTrigger(timer);
        if (Objects.isNull(trigger)) {
            return false;
        }
        Class<?> aClass = ClassLoaderUtil.loadClass(timer.getClassPath());
        if (!AbstractJob.class.isAssignableFrom(aClass)) {
            return false;
        }
        AbstractJob job = (AbstractJob) ReflectUtil.newInstance(aClass);
        ScheduledFuture<?> schedule = scheduler.schedule(job, trigger);
        TASK_MAP.put(timer.getKey(), schedule);
        log.info("[{}]任务 => expression: {}, class: {} 加载完成", timer.getName(), timer.getExpression(), timer.getClassPath());
        return true;
    }

    @Override
    public boolean removeSchedule(SysTimer timer) {
        if (StrUtil.isBlank(timer.getKey())) {
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
    public boolean execSchedule(SysTimer timer) {
        if (StrUtil.isBlank(timer.getClassPath())) {
            return false;
        }
        Class<?> aClass = ClassLoaderUtil.loadClass(timer.getClassPath());
        if (!AbstractJob.class.isAssignableFrom(aClass)) {
            return false;
        }
        AbstractJob job = (AbstractJob) ReflectUtil.newInstance(aClass);
        scheduler.getScheduledThreadPoolExecutor().execute(job);
        return true;
    }

    private Trigger getTrigger(SysTimer timer) {
        String expression = timer.getExpression();
        Integer type = timer.getType();
        if (type == 0) {
            return new RangeCronTrigger(timer.getKey(), expression, timer.getStartTime(), timer.getEndTime());
        } else if (type == 1) {
            DateTime date = DateUtil.parse(expression, "yyyy-MM-dd HH:mm:ss");
            return new OnlyTrigger(timer.getKey(), date);
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
        if (StrUtil.isBlank(json)) {
            return Collections.emptyMap();
        }
        //noinspection unchecked
        return JsonUtil.toObject(json, Map.class);
    }
}
