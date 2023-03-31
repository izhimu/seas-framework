package com.izhimu.seas.core.scan;


import java.util.HashSet;
import java.util.Set;

/**
 * 扫描处理器持有
 *
 * @author haoran
 * @version v1.0
 */
public class ScanHandlerHolder {

    private static final Set<IScanHandler> HANDLER_SET = new HashSet<>();

    /**
     * 添加处理器
     *
     * @param handler {@link IScanHandler 扫描处理器}
     */
    public static void add(IScanHandler handler) {
        HANDLER_SET.add(handler);
    }

    /**
     * 获取全部
     *
     * @return {@link IScanHandler 扫描处理器集合}
     */
    public static Set<IScanHandler> all() {
        return HANDLER_SET;
    }
}
