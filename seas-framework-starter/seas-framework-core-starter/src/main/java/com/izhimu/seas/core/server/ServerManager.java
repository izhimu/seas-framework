package com.izhimu.seas.core.server;

import com.izhimu.seas.core.pool.ThreadPoolFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 服务管理器
 *
 * @author Haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public class ServerManager {

    /**
     * 线程池
     */
    private final ExecutorService executorService;

    private final List<IServer> serverList;

    public ServerManager() {
        executorService = ThreadPoolFactory.build("Server");
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
        log.info("ServerManager loading {}", server.getClass().getSimpleName());
        return this;
    }

    /**
     * 启动所有服务
     */
    public void start() {
        serverList.forEach(executorService::execute);
        log.info("ServerManager start");
    }

    /**
     * 停止所有服务
     */
    public void stop() {
        serverList.forEach(IServer::shutdown);
        executorService.shutdown();
        log.info("ServerManager stop");
    }
}
