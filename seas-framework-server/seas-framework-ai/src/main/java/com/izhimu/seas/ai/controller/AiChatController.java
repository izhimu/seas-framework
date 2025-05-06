package com.izhimu.seas.ai.controller;

import com.izhimu.seas.ai.entity.AiInput;
import com.izhimu.seas.ai.entity.AiOutput;
import com.izhimu.seas.ai.service.AiChatService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.annotation.React;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * AI 语言模型控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/ai/chat")
public class AiChatController {

    @Resource
    private AiChatService aiChatService;

    @Resource
    private ChatModel chatModel;

    @OperationLog("AI对话-发起对话")
    @PostMapping
    public AiOutput chat(@RequestBody AiInput input) {
        return aiChatService.chat(input);
    }

    @OperationLog("AI对话-发起流式对话")
    @React
    @PostMapping("/stream")
    public Flux<String> chatStream(@RequestBody AiInput input) {
        Flux<ChatResponse> response = chatModel.stream(new Prompt(input.getMsg()));
        return response.map(v -> v.getResult().getOutput().getText());
    }
}
