package com.izhimu.seas.base.listener;

import com.izhimu.seas.base.service.SysAccountLogService;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.security.event.LogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录日志监听器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Component
public class LoginLogListener implements ApplicationListener<LogEvent> {

    @Resource
    private SysAccountLogService service;

    @Async
    @Override
    public void onApplicationEvent(@NonNull LogEvent event) {
        log.debug("LoginLogListener -> {}", JsonUtil.toJsonStr(event));
        service.saveLog(event.getLoginDTO(), event.getStatus());
    }
}
