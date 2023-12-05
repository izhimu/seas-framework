package com.izhimu.seas.core.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.izhimu.seas.core.utils.LogUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 抽象任务类
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Data
public abstract class AbstractJob implements Runnable {

    private String key;

    private Map<String, Object> param;

    private long start;

    private long end;

    @SuppressWarnings("unused")
    public abstract void run(Map<String, Object> param);

    @Override
    public void run() {
        log.info(LogUtil.format("Job", key, "Start", param));
        TimeInterval timer = DateUtil.timer();
        run(param);
        log.info(LogUtil.format("Job", key, "Done {}ms", param), timer.interval());
    }
}
