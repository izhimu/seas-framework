package com.izhimu.seas.ai.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * AI输出数据
 *
 * @author haoran
 */
@Data
public class AiOutput implements Serializable {

    /**
     * 聊天ID
     */
    private Long chatId;
    /**
     * 用户输入的消息
     */
    private String msg;
}
