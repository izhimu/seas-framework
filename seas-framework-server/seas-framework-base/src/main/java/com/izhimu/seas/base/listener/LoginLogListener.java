package com.izhimu.seas.base.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.SysAccountLogService;
import com.izhimu.seas.core.web.entity.Login;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEvent;
import com.izhimu.seas.core.event.IEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录日志监听器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@EventListener
public class LoginLogListener implements IEventListener {

    private final SysAccountLogService service;

    public LoginLogListener() {
        service = SpringUtil.getBean(SysAccountLogService.class);
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
    public IEvent getEvent() {
        return CoreEvent.E_LOGIN;
    }

    @Override
    public boolean async() {
        return true;
    }
}
