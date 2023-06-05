package com.izhimu.seas.job.service;

import com.izhimu.seas.job.entity.SysTimer;

/**
 * 定时服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface ScheduleService {

    /**
     * 新增定时任务
     *
     * @param timer SysTimer
     * @return boolean
     */
    boolean add(SysTimer timer);

    /**
     * 删除定时任务
     *
     * @param timer SysTimer
     * @return boolean
     */
    boolean del(SysTimer timer);

    /**
     * 执行定时任务
     *
     * @param timer SysTimer
     * @return boolean
     */
    boolean run(SysTimer timer);

    /**
     * 是否在执行列表中
     *
     * @param key String
     * @return boolean
     */
    boolean has(String key);
}
