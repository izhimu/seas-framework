package com.izhimu.seas.ai.service;

/**
 * Dify API服务
 *
 * @author haoran
 */
public interface DifyApiService {

    /**
     * POST
     */
    <T> T post(String api, String key, String param, Class<T> clazz);
}
