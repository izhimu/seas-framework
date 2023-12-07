package com.izhimu.seas.core.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.izhimu.seas.core.log.LogWrapper;
import lombok.Data;

import java.util.Map;

/**
 * 抽象任务类
 *
 * @author haoran
 * @version v1.0
 */
@Data
public abstract class AbstractJob implements Runnable {

    private static final LogWrapper log = LogWrapper.build("Job");

    private String key;

    private Map<String, Object> param;

    private long start;

    private long end;

    @SuppressWarnings("unused")
    public abstract void run(Map<String, Object> param);

    @Override
    public void run() {
        log.info(key, param, "Start");
        TimeInterval timer = DateUtil.timer();
        run(param);
        log.info(key, param, "Done {}ms", timer.interval());
    }
}
