package com.izhimu.seas.job.jobs;

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

    private String key;

    private Map<String, Object> param;

    public abstract void run(Map<String, Object> param);

    @Override
    public void run() {
        run(param);
    }
}
