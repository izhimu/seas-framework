package com.izhimu.seas.core.scan;

import org.reflections.Reflections;

/**
 * 扫描处理类接口
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public interface IScanHandler {

    /**
     * 扫描器名称
     *
     * @return 名称
     */
    String name();

    /**
     * 扫描处理
     */
    void scan(Reflections reflections);
}
