package com.izhimu.seas.core.event;

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
     * 拷贝数据
     *
     * @param data 数据
     * @return 拷贝后数据
     */
    default Object copy(Object data) {
        return data;
    }
}
