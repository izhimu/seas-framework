package com.izhimu.seas.job.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.job.entity.SysTimer;
import com.izhimu.seas.job.mapper.SysTimerMapper;
import com.izhimu.seas.job.service.ScheduleService;
import com.izhimu.seas.job.service.SysTimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class SysTimerServiceImpl extends BaseServiceImpl<SysTimerMapper, SysTimer> implements SysTimerService {

    @Resource
    private ScheduleService scheduleService;

    @Override
    public Long add(SysTimer entity) {
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
    public boolean updateById(SysTimer entity) {
        super.updateById(entity);
        if (Objects.isNull(entity.getStartTime()) || Objects.isNull(entity.getEndTime())) {
            this.lambdaUpdate()
                    .eq(SysTimer::getId, entity.getId())
                    .set(SysTimer::getStartTime, entity.getStartTime())
                    .set(SysTimer::getEndTime, entity.getEndTime())
                    .update();
        }
        SysTimer timer = this.getById(entity.getId());
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
        SysTimer timer = this.getById(id);
        if (scheduleService.has(timer.getKey())) {
            scheduleService.del(timer);
        }
        return super.removeById(id);
    }

    @Override
    public boolean initSchedule() {
        log.info("加载定时任务 => 开始");
        List<SysTimer> list = this.lambdaQuery()
                .eq(SysTimer::getStatus, 1)
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
        log.info("加载定时任务 <= 结束");
        return true;
    }

    @Override
    public boolean execSchedule(SysTimer timer) {
        timer = this.getById(timer.getId());
        return scheduleService.run(timer);
    }
}
