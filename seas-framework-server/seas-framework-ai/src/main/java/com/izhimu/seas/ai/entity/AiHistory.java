package com.izhimu.seas.ai.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.ai.chat.messages.MessageType;

import java.time.LocalDateTime;

/**
 * AI聊天记录
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_AI_HISTORY")
public class AiHistory extends IdEntity {

    /**
     * 聊天ID
     */
    private Long chatId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 角色
     */
    private MessageType role;

    /**
     * token
     */
    private Long token;

    /**
     * 总token
     */
    private Long totalToken;

    /**
     * 消息
     */
    private byte[] message;

    /**
     * 用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
