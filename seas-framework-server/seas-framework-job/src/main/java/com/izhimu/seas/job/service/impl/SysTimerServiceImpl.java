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
import java.util.concurrent.atomic.AtomicInteger;

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
            scheduleService.add(entity);
        }
        return super.add(entity);
    }

    @Override
    public boolean updateById(SysTimer entity) {
        super.updateById(entity);
        SysTimer timer = this.getById(entity.getId());
        if (scheduleService.has(timer.getKey())) {
            scheduleService.del(timer);
        }
        if (timer.getStatus() == 1) {
            scheduleService.add(timer);
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
        AtomicInteger count = new AtomicInteger();
        List<SysTimer> list = this.lambdaQuery()
                .eq(SysTimer::getStatus, 1)
                .list();
        list.forEach(timer -> {
            boolean add = scheduleService.add(timer);
            if (add) {
                count.getAndIncrement();
            }
        });
        log.info("加载定时任务 <= 结束 | 任务数：{}", count);
        return true;
    }

    @Override
    public boolean execSchedule(SysTimer timer) {
        timer = this.getById(timer.getId());
        return scheduleService.run(timer);
    }
}
