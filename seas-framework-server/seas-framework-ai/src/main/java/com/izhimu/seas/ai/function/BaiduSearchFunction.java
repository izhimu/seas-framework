package com.izhimu.seas.ai.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;

@Slf4j
@Description("百度搜索功能")
public class BaiduSearchFunction extends ToolsFunction<BaiduSearchFunction.Request, BaiduSearchFunction.Response> {

    @Override
    public Response apply(Request request) {
        log.info("BaiduSearchFunction --> request: {}", request);
        return new Response("未找到相关结果");
    }

    public record Request(String search) {
    }

    public record Response(String result) {
    }
}
