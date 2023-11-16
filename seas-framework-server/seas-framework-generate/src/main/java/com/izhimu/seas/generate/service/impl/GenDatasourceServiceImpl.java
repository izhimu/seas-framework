package com.izhimu.seas.generate.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.generate.db.engine.AbstractDbEngine;
import com.izhimu.seas.generate.db.engine.DbEngineFactory;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import com.izhimu.seas.generate.entity.GenDatasource;
import com.izhimu.seas.generate.mapper.GenDatasourceMapper;
import com.izhimu.seas.generate.service.GenDatasourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 数据源服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GenDatasourceServiceImpl extends BaseServiceImpl<GenDatasourceMapper, GenDatasource> implements GenDatasourceService {
    @Override
    public boolean test(Long id) throws DbEngineException {
        GenDatasource datasource = this.getById(id);
        try (AbstractDbEngine engine = DbEngineFactory.getEngine(datasource.getDsType(), datasource.getDsUrl(), datasource.getDsUser(), datasource.getDsPwd())) {
            boolean connected = Objects.nonNull(engine) && engine.testConnection();
            if (connected && !engine.testDatabase(datasource.getDbName())) {
                throw new DbEngineException(datasource.getDbName() + " 数据库不存在");
            }
            return connected;
        } catch (Exception e) {
            throw new DbEngineException(e.getMessage());
        }
    }
}
