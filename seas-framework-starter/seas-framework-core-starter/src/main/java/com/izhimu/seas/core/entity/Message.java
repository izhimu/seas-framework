package com.izhimu.seas.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息实体
 *
 * @author haoran
 */
@Data
public class Message<T extends Serializable> {

    /**
     * 消息类型
     */
    private String type;
    /**
     * 跟踪ID
     */
    private String trackId;
    /**
     * 时间戳
     */
    private Long timestamp;
}
