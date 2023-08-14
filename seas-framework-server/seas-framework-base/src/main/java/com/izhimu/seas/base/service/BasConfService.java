package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.BasConf;
import com.izhimu.seas.data.service.IBaseService;

/**
 * 配置信息服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasConfService extends IBaseService<BasConf> {

    /**
     * 根据Key获取配置值
     *
     * @param key key
     * @return value
     */
    String getValueByKey(String key);

    /**
     * key是否可用
     *
     * @param id  Long
     * @param key String
     * @return boolean
     */
    boolean usableKey(Long id, String key);
}
