package com.izhimu.seas.security.event;

import com.izhimu.seas.core.dto.LoginDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 登录日志事件
 *
 * @author haoran
 * @version v1.0
 */
@Getter
public class LoginLogEvent extends ApplicationEvent {

    private final LoginDTO loginDTO;

    private final Integer status;

    public LoginLogEvent(Object source, LoginDTO loginDTO, Integer status) {
        super(source);
        this.loginDTO = loginDTO;
        this.status = status;
    }
}
