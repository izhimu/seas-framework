package com.izhimu.seas.core.server;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 服务管理器
 *
 * @author Haoran
 * @version v1.0
 * @date 2021/12/7 15:06
 */
@SuppressWarnings("unused")
@Slf4j
public class ServerManager {

    /**
     * 线程池
     */
    private final ExecutorService executorService;

    private final List<IServer> serverList;

    public ServerManager() {
        executorService = ThreadUtil.newExecutor();
        serverList = new ArrayList<>();
    }

    /**
     * 添加服务列表
     *
     * @param server 服务实现 {@link IServer Server}
     * @return 服务管理器链
     */
    public ServerManager add(IServer server) {
        serverList.add(server);
        log.info("服务管理器加载 -> {}", server);
        return this;
    }

    /**
     * 启动所有服务
     */
    public void start() {
        serverList.forEach(executorService::execute);
        log.info("服务管理器启动服务 -> 服务数[{}]", serverList.size());
    }

    /**
     * 停止所有服务
     */
    public void stop() {
        serverList.forEach(IServer::shutdown);
        executorService.shutdown();
        log.info("服务管理器停止服务 -> 服务数[{}]", serverList.size());
    }
}
