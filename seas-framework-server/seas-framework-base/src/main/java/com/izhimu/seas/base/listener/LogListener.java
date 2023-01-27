package com.izhimu.seas.base.listener;

import com.izhimu.seas.base.service.SysLogService;
import com.izhimu.seas.core.event.LogEvent;
import com.izhimu.seas.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 操作日志监听器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Component
public class LogListener implements ApplicationListener<LogEvent> {

    @Resource
    private SysLogService service;

    @Async
    @Override
    public void onApplicationEvent(@NonNull LogEvent event) {
        log.debug("LogListener -> {}", JsonUtil.toJsonStr(event));
        service.saveLog(event.getDto());
    }
}
