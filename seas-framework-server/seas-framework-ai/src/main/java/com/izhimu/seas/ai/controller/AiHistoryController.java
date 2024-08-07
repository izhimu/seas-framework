package com.izhimu.seas.ai.controller;

import com.izhimu.seas.ai.entity.AiHistory;
import com.izhimu.seas.ai.service.AiHistoryService;
import com.izhimu.seas.core.annotation.OperationLog;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * AI 对话历史控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/ai/history")
public class AiHistoryController {

    @Resource
    private AiHistoryService aiHistoryService;

    @OperationLog("AI对话历史-记录查询")
    @PostMapping("/list/{chatId}")
    public List<AiHistory> list(@PathVariable Long chatId) {
        return aiHistoryService
                .lambdaQuery()
                .eq(AiHistory::getChatId, chatId)
                .notIn(AiHistory::getRole, List.of(MessageType.SYSTEM, MessageType.TOOL))
                .orderByAsc(AiHistory::getSort)
                .list()
                .stream()
                .peek(v -> v.setMessageStr(new String(v.getMessage(), StandardCharsets.UTF_8)))
                .toList();
    }
}
