package com.izhimu.seas.message.controller;

import com.izhimu.seas.core.annotation.React;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Server-Sent Events 控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
public class SSEController {

    @React
    @GetMapping("/sse")
    public Flux<String> sseEmitter() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> "data: " + seq);
    }
}
