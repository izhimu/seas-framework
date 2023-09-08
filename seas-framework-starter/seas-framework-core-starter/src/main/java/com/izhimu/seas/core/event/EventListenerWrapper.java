package com.izhimu.seas.core.event;

import lombok.Data;

/**
 * 事件监听器包装
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class EventListenerWrapper {

    /**
     * 监听器
     */
    private IEventListener listener;
    /**
     * 监听的事件
     */
    private String[] events;
    /**
     * 排序
     */
    private int order;
    /**
     * 异步执行
     */
    private boolean async;
}
