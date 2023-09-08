package com.izhimu.seas.base.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.BasLogService;
import com.izhimu.seas.core.entity.Log;
import com.izhimu.seas.core.event.CoreEvent;
import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEventListener;

/**
 * 操作日志监听器
 *
 * @author haoran
 * @version v1.0
 */
@EventListener(value = CoreEvent.E_LOG, async = true)
public class LogListener implements IEventListener {

    private final BasLogService service;

    public LogListener() {
        service = SpringUtil.getBean(BasLogService.class);
    }

    @Override
    public boolean onEvent(Object data) {
        if (data instanceof Log logInfo) {
            service.saveLog(logInfo);
            return true;
        }
        return false;
    }
}
