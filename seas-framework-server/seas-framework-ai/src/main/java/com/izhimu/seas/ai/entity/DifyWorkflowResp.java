package com.izhimu.seas.ai.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * Dify 工作流返回结果
 *
 * @author haoran
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DifyWorkflowResp implements Serializable {

    /**
     * ID
     */
    private String id;
    /**
     * 关联Workflow ID
     */
    private String workflowId;
    /**
     * 状态
     */
    private String status;
    /**
     * 输出
     */
    private Map<String, Object> outputs;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 耗时
     */
    private Float elapsedTime;
    /**
     * 总Token数
     */
    private Integer totalTokens;
    /**
     * 总部署
     */
    private Integer totalSteps;
    /**
     * 开始时间
     */
    private Long createdAt;
    /**
     * 结束时间
     */
    private Long finishedAt;
}
