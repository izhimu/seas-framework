package com.izhimu.seas.core.server;

/**
 * 服务接口
 *
 * @author Haoran
 * @version v1.0
 */
public interface IServer extends Runnable {

    /**
     * 停止线程
     */
    default void shutdown() {
        Thread.currentThread().interrupt();
    }
}
