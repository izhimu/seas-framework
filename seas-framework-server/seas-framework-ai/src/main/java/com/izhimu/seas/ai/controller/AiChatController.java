package com.izhimu.seas.ai.controller;

import com.izhimu.seas.ai.function.BaiduSearchFunction;
import com.izhimu.seas.core.annotation.React;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

import static com.izhimu.seas.ai.function.ToolsFunction.FUNCTION_SYSTEM_MANAGER;

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

    @Resource
    private VectorStore vectorStore;

    @GetMapping("/generate")
    public String generate(String input) {
        UserMessage userMessage = new UserMessage(input);
        ChatResponse response = chatModel.call(new Prompt(List.of(new SystemMessage(FUNCTION_SYSTEM_MANAGER), userMessage),
                OllamaOptions.builder()
                        .withFunctionCallbacks(List.of(new BaiduSearchFunction().getWrapper()))
                        .build()));
        Generation result = response.getResult();
        AssistantMessage output = result.getOutput();
        return output.getContent();
    }

    @React
    @GetMapping("/stream/generate")
    public Flux<String> generateStream(String input) {
        Flux<ChatResponse> response = chatModel.stream(new Prompt(input));
        return response.map(v -> v.getResult().getOutput().getContent());
    }

    @GetMapping("/documents")
    public List<Document> getDocuments() {
        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));
        vectorStore.add(documents);
        List<Document> documentList = vectorStore.similaritySearch(SearchRequest.query("Spring").withTopK(5));
        vectorStore.delete(documentList.stream().map(Document::getId).toList());
        return documentList;
    }
}
