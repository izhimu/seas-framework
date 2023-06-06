package com.izhimu.seas.job.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.job.entity.JobTimer;

/**
 * 定时器服务层接口
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("UnusedReturnValue")
public interface JobTimerService extends IBaseService<JobTimer> {

    /**
     * 初始化任务
     *
     * @return boolean
     */
    boolean initSchedule();

    /**
     * 立即执行任务
     *
     * @param timer SysTimer
     * @return boolean
     */
    boolean execSchedule(JobTimer timer);
}
