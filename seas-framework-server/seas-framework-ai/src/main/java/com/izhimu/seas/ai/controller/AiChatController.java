package com.izhimu.seas.ai.controller;

import com.izhimu.seas.ai.entity.AiInput;
import com.izhimu.seas.ai.entity.AiOutput;
import com.izhimu.seas.ai.service.AiChatService;
import com.izhimu.seas.core.annotation.React;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

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
    private OllamaChatModel chatModel;

    @Resource
    private VectorStore vectorStore;

    @PostMapping
    public AiOutput chat(@RequestBody AiInput input) {
        return aiChatService.chat(input);
    }

    @React
    @PostMapping("/stream")
    public Flux<String> chatStream(@RequestBody AiInput input) {
        Flux<ChatResponse> response = chatModel.stream(new Prompt(input.getMsg()));
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
