package com.izhimu.seas.core.job;

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

    public abstract void run(Map<String, Object> param);

    public String getKey() {
        return key;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    @Override
    public void run() {
        log.info("执行任务[{}] => 开始 | 参数：{}", key, param);
        start = System.currentTimeMillis();
        run(param);
        end = System.currentTimeMillis();
        log.info("执行任务[{}] <= 结束 | 耗时：{}ms", key, end - start);
    }
}
