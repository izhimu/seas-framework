package com.izhimu.seas.ai.service.impl;

import com.izhimu.seas.ai.entity.AiHistory;
import com.izhimu.seas.ai.mapper.AiHistoryMapper;
import com.izhimu.seas.ai.service.AiHistoryService;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * AI聊天记录服务层实现
 *
 * @author haoran
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AiHistoryServiceImpl extends BaseServiceImpl<AiHistoryMapper, AiHistory> implements AiHistoryService {
    @Override
    public List<AiHistory> findHistoryByChatId(long chatId) {
        return this.lambdaQuery()
                .select(AiHistory::getSort, AiHistory::getRole, AiHistory::getTotalToken, AiHistory::getMessage)
                .eq(AiHistory::getChatId, chatId)
                .orderByAsc(AiHistory::getSort)
                .list();
    }

    @Override
    public boolean saveSystemMessage(long chatId, String message) {
        AiHistory history = new AiHistory();
        history.setChatId(chatId);
        history.setSort(0);
        history.setRole(MessageType.SYSTEM);
        history.setToken(0L);
        history.setTotalToken(0L);
        history.setMessage(message.getBytes(StandardCharsets.UTF_8));
        return this.save(history);
    }

    @Override
    public boolean saveChatMessage(long chatId, AiHistory last, Usage usage, String userMessage, String assistantMessage) {
        AiHistory history1 = new AiHistory();
        history1.setChatId(chatId);
        history1.setSort(last == null ? 1 : last.getSort() + 1);
        history1.setRole(MessageType.USER);
        history1.setToken(usage.getPromptTokens());
        history1.setTotalToken((last == null ? 0 : last.getTotalToken()) + usage.getPromptTokens());
        history1.setMessage(userMessage.getBytes(StandardCharsets.UTF_8));
        AiHistory history2 = new AiHistory();
        history2.setChatId(chatId);
        history2.setSort(history1.getSort() + 1);
        history2.setRole(MessageType.ASSISTANT);
        history2.setToken(usage.getGenerationTokens());
        history2.setTotalToken(history1.getTotalToken() + usage.getGenerationTokens());
        history2.setMessage(assistantMessage.getBytes(StandardCharsets.UTF_8));
        return this.save(history1) && this.save(history2);
    }
}
