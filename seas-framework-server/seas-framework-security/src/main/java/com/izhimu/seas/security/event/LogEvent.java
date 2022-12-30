package com.izhimu.seas.security.event;

import com.izhimu.seas.security.dto.LoginDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Describe
 *
 * @author haoran
 * @version v1.0
 */
@Getter
public class LogEvent extends ApplicationEvent {

    private final LoginDTO loginDTO;

    private final Integer status;

    public LogEvent(Object source, LoginDTO loginDTO, Integer status) {
        super(source);
        this.loginDTO = loginDTO;
        this.status = status;
    }
}
