package com.izhimu.seas.core.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器接口，用于多配置拓展加载
 *
 * @author haoran
 * @version v1.0
 */
public interface IExpansionInterceptor extends HandlerInterceptor {

    /**
     * 拦截路径
     *
     * @return 路径
     */
    default String[] addPath() {
        return new String[]{"/**"};
    }

    /**
     * 排除路径
     *
     * @return 路径
     */
    default String[] excludePath() {
        return new String[0];
    }
}
