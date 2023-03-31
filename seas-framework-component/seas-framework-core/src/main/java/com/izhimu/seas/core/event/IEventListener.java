package com.izhimu.seas.core.event;

import java.util.List;

/**
 * 事件监听器
 *
 * @author haoran
 * @version v1.0
 * 2021/11/2 14:10
 */
@SuppressWarnings("SameReturnValue")
public interface IEventListener {

    /**
     * 执行事件
     *
     * @param data 参数
     * @return true|false
     */
    boolean onEvent(Object data);

    /**
     * 获取事件类型
     *
     * @return {@link IEvent Event}
     */
    default IEvent getEvent() {
        return null;
    }

    /**
     * 获取事件类型
     *
     * @return {@link IEvent Event}
     */
    default List<IEvent> getEvents() {
        return null;
    }


    /**
     * 获取排序
     *
     * @return 排序序号
     */
    default int getOrder() {
        return 0;
    }

    /**
     * 是否并行
     *
     * @return 异步true|同步false
     */
    default boolean async() {
        return false;
    }

    /**
     * 拷贝数据
     *
     * @param data 数据
     * @return 拷贝后数据
     */
    default Object copy(Object data) {
        return data;
    }
}
