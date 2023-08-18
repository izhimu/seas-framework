package com.izhimu.seas.core.enums;

import com.izhimu.seas.core.event.IEvent;

/**
 * 核心事件
 *
 * @author haoran
 * @version v1.0
 */
public enum CoreEvent implements IEvent {

    /**
     * 登录事件
     */
    E_LOGIN,
    /**
     * 退出事件
     */
    E_LOGOUT,
    /**
     * 日志记录
     */
    E_LOG,
    /**
     * 刷新Session
     */
    E_SESSION_REFRESH
}
