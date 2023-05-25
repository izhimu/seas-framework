package com.izhimu.seas.job.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时器视图实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysTimerVO implements Serializable {

    private Long id;
    /**
     * 定时器名称
     */
    private String name;
    /**
     * 定时器标识
     */
    private String key;
    /**
     * 定时器类型
     * 0.CRON 1.定时执行 2.间隔时间
     */
    private Integer type;
    /**
     * 定时表达式
     */
    private String expression;
    /**
     * 定时器类路径
     */
    private String classPath;
    /**
     * 参数
     */
    private String param;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 状态
     * 0.就绪 1.运行 2.完成
     */
    private Integer status;
}
