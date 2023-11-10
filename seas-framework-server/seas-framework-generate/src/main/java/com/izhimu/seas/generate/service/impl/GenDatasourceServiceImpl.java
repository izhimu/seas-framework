package com.izhimu.seas.generate.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.generate.db.engine.AbstractDbEngine;
import com.izhimu.seas.generate.db.engine.DbEngineFactory;
import com.izhimu.seas.generate.entity.GenDatasource;
import com.izhimu.seas.generate.mapper.GenDatasourceMapper;
import com.izhimu.seas.generate.service.GenDatasourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

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
    public boolean test(Long id) {
        GenDatasource datasource = this.getById(id);
        AbstractDbEngine engine = DbEngineFactory.getEngine(datasource.getDsType(), datasource.getDsUrl(), datasource.getDsUser(), datasource.getDsPwd());
        return Objects.nonNull(engine) && engine.testConnection();
    }
}
