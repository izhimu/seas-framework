package com.izhimu.seas.workflow.service.impl;

import com.izhimu.seas.workflow.service.WorRepositoryService;
import jakarta.annotation.Resource;
import org.flowable.engine.RepositoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 流程资源接口实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WorRepositoryServiceImpl implements WorRepositoryService {

    @Resource
    private RepositoryService service;

    @Override
    public RepositoryService getNativeService() {
        return service;
    }
}
