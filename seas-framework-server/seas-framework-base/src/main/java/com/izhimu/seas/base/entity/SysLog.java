package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.enums.SearchType;
import com.izhimu.seas.data.handler.JsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_SYS_LOG")
public class SysLog extends IdEntity {

    /**
     * 请求地址
     */
    @Search
    private String requestUrl;
    /**
     * 请求方式
     */
    @Search(type = SearchType.EQUALS)
    private String method;
    /**
     * 请求参数
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private String params;
    /**
     * 响应结果
     */
    @TableField(typeHandler = JsonTypeHandler.class)
    private String result;
    /**
     * 请求时间
     */
    @OrderBy(asc = false)
    private LocalDateTime requestDate;
    /**
     * 操作人
     */
    @Search(type = SearchType.EQUALS)
    private Long userId;
    /**
     * 账号
     */
    private String account;
    /**
     * 操作人
     */
    private String userName;
    /**
     * 日志名称
     */
    @Search
    private String logName;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 请求状态
     */
    @Search
    private String status;
    /**
     * 执行用时
     */
    private Integer runtime;
    // Param
    /**
     * 请求时间
     */
    @ViewIgnore
    @TableField(exist = false)
    @Search(name = "request_date", type = SearchType.GE)
    private LocalDateTime requestDateStart;
    /**
     * 请求时间
     */
    @ViewIgnore
    @TableField(exist = false)
    @Search(name = "request_date", type = SearchType.LE)
    private LocalDateTime requestDateEnd;
    // View
    /**
     * 执行用时
     */
    @TableField(exist = false)
    private String runtimeValue;
}
