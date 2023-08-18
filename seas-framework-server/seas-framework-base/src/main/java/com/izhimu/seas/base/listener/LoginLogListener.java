package com.izhimu.seas.base.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.BasAccountLogService;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEvent;
import com.izhimu.seas.core.event.IEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 登录日志监听器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@EventListener
public class LoginLogListener implements IEventListener {

    private final BasAccountLogService service;

    public LoginLogListener() {
        service = SpringUtil.getBean(BasAccountLogService.class);
    }

    @Override
    public boolean onEvent(Object data) {
        if (data instanceof Login login) {
            service.saveLog(login, login.getStatus());
            return true;
        }
        return false;
    }

    @Override
    public List<IEvent> getEvents() {
        return List.of(CoreEvent.E_LOGIN, CoreEvent.E_LOGOUT);
    }

    @Override
    public boolean async() {
        return true;
    }
}
