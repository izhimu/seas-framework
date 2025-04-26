package com.izhimu.seas.ai.service;

import com.izhimu.seas.ai.entity.DifyWorkflowReq;
import com.izhimu.seas.ai.entity.DifyWorkflowResp;

/**
 * Dify 工作流服务
 *
 * @author haoran
 */
@SuppressWarnings("unused")
public interface DifyWorkflowService {

    /**
     * 运行工作流
     *
     * @param req 请求体
     * @return DifyWorkflowResp
     */
    DifyWorkflowResp run(String key, DifyWorkflowReq req);
}
