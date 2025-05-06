package com.izhimu.seas.ai.service.impl;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.ai.entity.AiHistory;
import com.izhimu.seas.ai.entity.AiInput;
import com.izhimu.seas.ai.entity.AiOutput;
import com.izhimu.seas.ai.service.AiChatService;
import com.izhimu.seas.ai.service.AiHistoryService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * AI聊天服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AiChatServiceImpl implements AiChatService {

    @Resource
    private ChatModel chatModel;

    @Resource
    private AiHistoryService aiHistoryService;


    @Override
    public AiOutput chat(AiInput input) {
        if (Objects.isNull(input.getChatId())) {
            input.setChatId(IdUtil.getSnowflakeNextId());
        }
        List<AiHistory> historyList = aiHistoryService.findHistoryByChatId(input.getChatId());
        boolean isNewChat = historyList.isEmpty();
        // 发起请求
        List<Message> messages = getHistoryMessages(historyList, isNewChat);
        messages.add(new UserMessage(input.getMsg()));
        ChatResponse response = chatModel.call(new Prompt(messages, ChatOptions.builder()
                .build()));
        String result = response.getResult().getOutput().getText();
        // 保存历史
        if (isNewChat) {
//            aiHistoryService.saveSystemMessage(input.getChatId(), FUNCTION_SYSTEM_MANAGER);
        }
        AiHistory last = isNewChat ? null : historyList.getLast();
        Usage usage = response.getMetadata().getUsage();
        aiHistoryService.saveChatMessage(input.getChatId(), last, usage, input.getMsg(), result);
        return output(input.getChatId(), result);
    }

    /**
     * 获取历史消息
     *
     * @param historyList 历史消息列表
     * @param isNewChat   是否是新聊天
     * @return 历史消息列表
     */
    private List<Message> getHistoryMessages(List<AiHistory> historyList, boolean isNewChat) {
        List<Message> messages = new ArrayList<>();
        if (isNewChat) {
//            messages.add(new SystemMessage(FUNCTION_SYSTEM_MANAGER));
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
                    .filter(Objects::nonNull)
                    .forEach(messages::add);
        }
        return messages;
    }

    /**
     * 输出结果
     *
     * @param chatId 聊天ID
     * @param msg    输出消息
     * @return 输出结果
     */
    private AiOutput output(long chatId, String msg) {
        AiOutput output = new AiOutput();
        output.setChatId(chatId);
        output.setMsg(msg);
        return output;
    }
}
