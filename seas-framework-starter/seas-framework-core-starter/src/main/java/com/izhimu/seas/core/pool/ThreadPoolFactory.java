package com.izhimu.seas.core.pool;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.setting.yaml.YamlUtil;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池构建工厂
 *
 * @author haoran
 */
public class ThreadPoolFactory {

    private static final String YML_NAME = "application.yml";
    private static final String YML_KEY = "seas.core.virtual-thread";

    private ThreadPoolFactory() {
    }

    private static boolean config() {
        Dict dict = YamlUtil.loadByPath(YML_NAME);
        return Optional.ofNullable((Boolean) BeanUtil.getProperty(dict, YML_KEY)).orElse(true);
    }

    public static ExecutorService build() {
        if (config()) {
            return Executors.newVirtualThreadPerTaskExecutor();
        } else {
            return Executors.newCachedThreadPool();
        }
    }

    public static ExecutorService build(String name) {
        if (config()) {
            return Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name(name.concat("-V#"), 1).factory());
        } else {
            return Executors.newCachedThreadPool(ThreadUtil.newNamedThreadFactory(name.concat("-"), false));
        }
    }
}
