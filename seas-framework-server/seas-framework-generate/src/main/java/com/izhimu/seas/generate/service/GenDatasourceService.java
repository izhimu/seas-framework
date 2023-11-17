package com.izhimu.seas.generate.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import com.izhimu.seas.generate.entity.GenDatasource;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询表列表
     *
     * @param id   Long
     * @param like String
     * @return List<String>
     */
    List<String> tables(Long id, String like);

    /**
     * 查询字段列表
     *
     * @param id        Long
     * @param tableName String
     * @return List<Map < String, Object>>
     */
    List<Map<String, Object>> fields(Long id, String tableName);

    /**
     * 查询表注释
     *
     * @param id        Long
     * @param tableName String
     * @return String
     */
    String tableComment(Long id, String tableName);
}
