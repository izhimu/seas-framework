package com.izhimu.seas.ai.service.impl;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.ai.entity.AiHistory;
import com.izhimu.seas.ai.entity.AiInput;
import com.izhimu.seas.ai.entity.AiOutput;
import com.izhimu.seas.ai.function.BaiduSearchFunction;
import com.izhimu.seas.ai.service.AiChatService;
import com.izhimu.seas.ai.service.AiHistoryService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.izhimu.seas.ai.function.ToolsFunction.FUNCTION_SYSTEM_MANAGER;

/**
 * AI聊天服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AiChatServiceImpl implements AiChatService {

    @Resource
    private OllamaChatModel chatModel;

    @Resource
    private AiHistoryService historyService;


    @Override
    public AiOutput chat(AiInput input) {
        if (Objects.isNull(input.getChatId())) {
            input.setChatId(IdUtil.getSnowflakeNextId());
        }
        List<AiHistory> historyList = historyService.lambdaQuery()
                .select(AiHistory::getSort, AiHistory::getRole, AiHistory::getTotalToken, AiHistory::getMessage)
                .eq(AiHistory::getChatId, input.getChatId())
                .orderByAsc(AiHistory::getSort)
                .list();
        boolean isNewChat = historyList.isEmpty();
        List<Message> messages = new ArrayList<>();
        AiHistory last = null;
        if (isNewChat) {
            messages.add(new SystemMessage(FUNCTION_SYSTEM_MANAGER));
        } else {
            historyList
                    .stream()
                    .map(v -> switch (v.getRole()) {
                        case MessageType.SYSTEM ->
                                new SystemMessage(new String(v.getMessage(), StandardCharsets.UTF_8));
                        case MessageType.USER -> new UserMessage(new String(v.getMessage(), StandardCharsets.UTF_8));
                        case MessageType.ASSISTANT ->
                                new AssistantMessage(new String(v.getMessage(), StandardCharsets.UTF_8));
                        default -> null;
                    })
                    .forEach(messages::add);
            last = historyList.getLast();
        }
        messages.add(new UserMessage(input.getMsg()));
        ChatResponse response = chatModel.call(new Prompt(messages, OllamaOptions.builder()
                .withFunctionCallbacks(List.of(new BaiduSearchFunction().getWrapper()))
                .build()));
        String result = response.getResult().getOutput().getContent();
        // 保存历史
        long chatId = isNewChat ? IdUtil.getSnowflakeNextId() : input.getChatId();
        Usage usage = response.getMetadata().getUsage();
        if (isNewChat) {
            AiHistory history0 = new AiHistory();
            history0.setChatId(chatId);
            history0.setSort(0);
            history0.setRole(MessageType.SYSTEM);
            history0.setToken(0L);
            history0.setTotalToken(0L);
            history0.setMessage(FUNCTION_SYSTEM_MANAGER.getBytes(StandardCharsets.UTF_8));
            historyService.save(history0);
        }
        AiHistory history1 = new AiHistory();
        history1.setChatId(chatId);
        history1.setSort(last == null ? 1 : last.getSort() + 1);
        history1.setRole(MessageType.USER);
        history1.setToken(usage.getPromptTokens());
        history1.setTotalToken((last == null ? 0 : last.getTotalToken()) + usage.getPromptTokens());
        history1.setMessage(input.getMsg().getBytes(StandardCharsets.UTF_8));
        historyService.save(history1);
        AiHistory history2 = new AiHistory();
        history2.setChatId(chatId);
        history2.setSort(history1.getSort() + 1);
        history2.setRole(MessageType.ASSISTANT);
        history2.setToken(usage.getGenerationTokens());
        history2.setTotalToken(history1.getTotalToken() + usage.getGenerationTokens());
        history2.setMessage(result.getBytes(StandardCharsets.UTF_8));
        historyService.save(history2);
        AiOutput output = new AiOutput();
        output.setChatId(chatId);
        output.setMsg(result);
        return output;
    }
}
