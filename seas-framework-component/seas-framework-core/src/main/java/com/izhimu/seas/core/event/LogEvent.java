package com.izhimu.seas.core.event;

import com.izhimu.seas.core.dto.SysLogDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 登录日志事件
 *
 * @author haoran
 * @version v1.0
 */
@Getter
public class LogEvent extends ApplicationEvent {

    private final SysLogDTO dto;

    public LogEvent(Object source, SysLogDTO dto) {
        super(source);
        this.dto = dto;
    }
}
