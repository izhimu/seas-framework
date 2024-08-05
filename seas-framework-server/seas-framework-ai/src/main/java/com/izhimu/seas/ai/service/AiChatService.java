package com.izhimu.seas.ai.service;

import com.izhimu.seas.ai.entity.AiInput;
import com.izhimu.seas.ai.entity.AiOutput;

/**
 * AI聊天服务层接口
 */
public interface AiChatService {

    /**
     * 聊天接口
     *
     * @param input 输入参数
     * @return 输出结果
     */
    AiOutput chat(AiInput input);
}
