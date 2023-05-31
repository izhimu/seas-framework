package com.izhimu.seas.job.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.enums.SearchType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 定时器实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_SYS_TIMER")
public class SysTimer extends IdEntity {

    /**
     * 定时器名称
     */
    @Search
    private String name;
    /**
     * 定时器标识
     */
    @Search
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
    @Search(type = SearchType.EQUALS)
    private Integer status;
}
