package com.izhimu.seas.core.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池构建工厂
 *
 * @author haoran
 */
public class ThreadPoolFactory {

    private ThreadPoolFactory() {
    }

    public static ExecutorService build() {
        return Executors.newVirtualThreadPerTaskExecutor();
    }

    public static ExecutorService build(String name) {
        return Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name(name.concat("-V#"), 1).factory());
    }
}
