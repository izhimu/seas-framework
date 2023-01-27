package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.mybatis.handler.JsonTypeHandler;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_LOG")
public class SysLog implements Serializable {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 请求方式
     */
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
    private LocalDateTime requestDate;
    /**
     * 操作人
     */
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
    private String status;
    /**
     * 执行用时
     */
    private Integer runtime;
}
