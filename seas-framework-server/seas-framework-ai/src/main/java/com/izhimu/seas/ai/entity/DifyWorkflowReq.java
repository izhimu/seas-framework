package com.izhimu.seas.ai.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nonnull;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Dify 工作流请求体
 *
 * @author haoran
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DifyWorkflowReq implements Serializable {

    /**
     * 输入参数
     */
    @Nonnull
    private Map<String, Object> inputs;
    /**
     * 响应模式
     */
    @Nonnull
    private String responseMode;
    /**
     * 用户标识
     */
    @Nonnull
    private String user;

    @SuppressWarnings("unused")
    @Data
    public static class Input implements Serializable {
        /**
         * 输入类型
         */
        private String type;
        /**
         * 传递方式
         */
        private String transferMethod;
        /**
         * 图片地址
         */
        private String url;
        /**
         * 上传文件ID
         */
        private String uploadFileId;
    }
}
