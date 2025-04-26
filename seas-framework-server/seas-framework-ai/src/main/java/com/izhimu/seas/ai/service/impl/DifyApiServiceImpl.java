package com.izhimu.seas.ai.service.impl;

import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.ai.config.AiConfig;
import com.izhimu.seas.ai.service.DifyApiService;
import com.izhimu.seas.core.utils.JsonUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * @author haoran
 */
@Service
public class DifyApiServiceImpl implements DifyApiService {

    @Resource
    private AiConfig config;

    @Override
    public <T> T post(String api, String key, String param, Class<T> clazz) {
        String endpoint = config.getDifyEndpoint();
        if (StrUtil.isBlankIfStr(endpoint)) {
            log.errorT("Dify", "endpoint is not configuration");
            return null;
        }
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + api))
                    .header("Authorization", "Bearer " + key)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(param))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (!Objects.equals(200, response.statusCode())) {
                log.errorT("Dify", "request {} status code is not 200, response: {}", api, response.body());
                return null;
            }
            return JsonUtil.toObject(response.body(), clazz);
        } catch (Exception e) {
            log.errorT("Dify", "post api error, api: {}, msg: {}", api, e.getMessage());
        }
        return null;
    }
}
