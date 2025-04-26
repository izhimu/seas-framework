package com.izhimu.seas.ai.service.impl;

import com.izhimu.seas.ai.entity.DifyWorkflowReq;
import com.izhimu.seas.ai.entity.DifyWorkflowResp;
import com.izhimu.seas.ai.service.DifyApiService;
import com.izhimu.seas.ai.service.DifyWorkflowService;
import com.izhimu.seas.core.utils.JsonUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * Dify 工作流 服务实现类
 *
 * @author haoran
 */
@Service
public class DifyWorkflowServiceImpl implements DifyWorkflowService {

    /**
     * 执行 workflow
     */
    private static final String API_RUN = "/workflows/run";

    @Resource
    private DifyApiService apiService;

    @Override
    public DifyWorkflowResp run(String key, DifyWorkflowReq req) {
        return apiService.post(API_RUN, key, JsonUtil.toJsonStr(req), DifyWorkflowResp.class);
    }
}
