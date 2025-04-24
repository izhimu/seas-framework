package com.izhimu.seas.core.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.Data;

import java.util.Map;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 抽象任务类
 *
 * @author haoran
 * @version v1.0
 */
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
        log.infoT("Scheduler", "run, key: {}, param: {}", key, param);
        TimeInterval timer = DateUtil.timer();
        run(param);
        log.infoT("Scheduler", "done {}ms, key: {}", timer.interval(), key);
    }
}
