package com.izhimu.seas.ai.controller;

import com.izhimu.seas.core.annotation.React;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

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
    private OllamaChatModel chatModel;

    @GetMapping("/generate")
    public String generate(String input) {
        UserMessage userMessage = new UserMessage(input);
        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage)));
        Generation result = response.getResult();
        AssistantMessage output = result.getOutput();
        return output.getContent();
    }

    @React
    @GetMapping("/stream/generate")
    public Flux<String> generateStream(String input) {
        UserMessage userMessage = new UserMessage(input);
        Flux<ChatResponse> response = chatModel.stream(new Prompt(List.of(userMessage)));
        return response.map(v -> v.getResult().getOutput().getContent());
    }
}
