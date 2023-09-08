package com.izhimu.seas.job.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.job.entity.JobTimer;
import com.izhimu.seas.job.mapper.JobTimerMapper;
import com.izhimu.seas.job.service.JobTimerService;
import com.izhimu.seas.job.service.ScheduleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 定时器服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class JobTimerServiceImpl extends BaseServiceImpl<JobTimerMapper, JobTimer> implements JobTimerService {

    @Resource
    private ScheduleService scheduleService;

    @Override
    public Long add(JobTimer entity) {
        if (Objects.isNull(entity.getStatus())) {
            entity.setStatus(0);
        }
        if (entity.getStatus() == 1) {
            boolean result = scheduleService.add(entity);
            if (!result) {
                entity.setStatus(3);
            }
        }
        return super.add(entity);
    }

    @Override
    public boolean updateById(JobTimer entity) {
        super.updateById(entity);
        if (Objects.isNull(entity.getStartTime()) || Objects.isNull(entity.getEndTime())) {
            this.lambdaUpdate()
                    .eq(JobTimer::getId, entity.getId())
                    .set(JobTimer::getStartTime, entity.getStartTime())
                    .set(JobTimer::getEndTime, entity.getEndTime())
                    .update();
        }
        JobTimer timer = this.getById(entity.getId());
        if (scheduleService.has(timer.getKey())) {
            scheduleService.del(timer);
        }
        if (timer.getStatus() == 1) {
            boolean result = scheduleService.add(timer);
            if (!result) {
                entity.setStatus(3);
                super.updateById(entity);
            }
        }
        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        JobTimer timer = this.getById(id);
        if (scheduleService.has(timer.getKey())) {
            scheduleService.del(timer);
        }
        return super.removeById(id);
    }

    @Override
    public boolean initSchedule() {
        log.info(LogUtil.format("TimerJob", "Loading"));
        TimeInterval t = DateUtil.timer();
        List<JobTimer> list = this.lambdaQuery()
                .eq(JobTimer::getStatus, 1)
                .list();
        list.stream()
                .filter(timer -> !scheduleService.has(timer.getKey()))
                .forEach(timer -> {
                    boolean result = scheduleService.add(timer);
                    if (!result) {
                        timer.setStatus(3);
                        this.updateById(timer);
                    }
                });
        log.info(LogUtil.format("TimerJob", "Load Done {}ms"), t.interval());
        return true;
    }

    @Override
    public boolean execSchedule(JobTimer timer) {
        timer = this.getById(timer.getId());
        return scheduleService.run(timer);
    }
}
