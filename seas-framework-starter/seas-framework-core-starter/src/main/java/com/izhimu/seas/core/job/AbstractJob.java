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
        log.infoT(key, "Job run, param={}", param);
        TimeInterval timer = DateUtil.timer();
        run(param);
        log.info(key, "Job done {}ms", timer.interval());
    }
}
