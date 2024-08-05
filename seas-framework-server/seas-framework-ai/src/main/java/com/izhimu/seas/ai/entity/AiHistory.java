package com.izhimu.seas.ai.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.ai.chat.messages.MessageType;

import java.time.LocalDateTime;

/**
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_AI_HISTORY")
public class AiHistory extends IdEntity {

    private Long chatId;

    private Long userId;

    private Integer sort;

    private MessageType role;

    private Integer token;

    private Integer totalToken;

    private byte[] message;

    private LocalDateTime time;
}
