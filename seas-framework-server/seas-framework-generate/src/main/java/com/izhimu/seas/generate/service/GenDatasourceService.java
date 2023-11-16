package com.izhimu.seas.generate.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import com.izhimu.seas.generate.entity.GenDatasource;

/**
 * 数据源服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface GenDatasourceService extends IBaseService<GenDatasource> {

    /**
     * 测试连接
     *
     * @param id Long
     * @return boolean
     */
    boolean test(Long id) throws DbEngineException;
}
