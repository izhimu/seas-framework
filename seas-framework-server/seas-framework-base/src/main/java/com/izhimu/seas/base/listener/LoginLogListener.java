package com.izhimu.seas.base.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.BasAccountLogService;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.event.CoreEvent;
import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEventListener;

/**
 * 登录日志监听器
 *
 * @author haoran
 * @version v1.0
 */
@EventListener(value = {CoreEvent.E_LOGIN, CoreEvent.E_LOGOUT}, async = true)
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
}
