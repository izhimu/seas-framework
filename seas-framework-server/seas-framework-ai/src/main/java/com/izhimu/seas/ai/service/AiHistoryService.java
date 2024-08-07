package com.izhimu.seas.ai.service;

import com.izhimu.seas.ai.entity.AiHistory;
import com.izhimu.seas.data.service.IBaseService;
import org.springframework.ai.chat.metadata.Usage;

import java.util.List;

/**
 * AI聊天记录服务层接口
 *
 * @author haoran
 */
public interface AiHistoryService extends IBaseService<AiHistory> {

    /**
     * 根据聊天ID查询聊天记录
     *
     * @param chatId 聊天ID
     * @return 聊天记录列表
     */
    List<AiHistory> findHistoryByChatId(long chatId);

    /**
     * 保存系统消息
     *
     * @param chatId  聊天ID
     * @param message 系统消息
     * @return 保存结果
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean saveSystemMessage(long chatId, String message);

    /**
     * 保存聊天消息
     *
     * @param chatId           聊天ID
     * @param last             上一条消息
     * @param usage            使用量
     * @param userMessage      用户消息
     * @param assistantMessage 机器人消息
     * @return 保存结果
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean saveChatMessage(long chatId, AiHistory last, Usage usage, String userMessage, String assistantMessage);
}
