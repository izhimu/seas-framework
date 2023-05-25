package com.izhimu.seas.core.web.entity;

import com.izhimu.seas.core.util.ViewUtil;

import java.util.Map;

/**
 * 视图接口
 *
 * @author haoran
 * @version v1.0
 */
public interface IView {

    /**
     * 转换view对象
     *
     * @return Map<String, Object>
     */
    default Map<String, Object> toView() {
        return ViewUtil.toView(this);
    }
}
