package com.izhimu.seas.ai.entity;

import lombok.Data;

/**
 * 用户输入数据
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class AiInput {

    /**
     * 聊天ID
     */
    private String chatId;
    /**
     * 用户输入的消息
     */
    private String msg;
}
