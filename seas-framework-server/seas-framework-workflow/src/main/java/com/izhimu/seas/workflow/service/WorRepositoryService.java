package com.izhimu.seas.workflow.service;

import org.flowable.engine.RepositoryService;

/**
 * 流程资源接口
 *
 * @author haoran
 * @version v1.0
 */
public interface WorRepositoryService {

    /**
     * 获取原生服务接口
     *
     * @return RepositoryService
     */
    RepositoryService getNativeService();
}
