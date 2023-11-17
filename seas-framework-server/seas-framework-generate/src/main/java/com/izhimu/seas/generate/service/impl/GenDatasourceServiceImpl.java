package com.izhimu.seas.generate.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.generate.db.engine.AbstractDbEngine;
import com.izhimu.seas.generate.db.engine.DbEngineFactory;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import com.izhimu.seas.generate.entity.GenDatasource;
import com.izhimu.seas.generate.mapper.GenDatasourceMapper;
import com.izhimu.seas.generate.service.GenDatasourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
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

    @Override
    public List<String> tables(Long id, String like) {
        GenDatasource datasource = this.getById(id);
        try (AbstractDbEngine engine = DbEngineFactory.getEngine(datasource.getDsType(), datasource.getDsUrl(), datasource.getDsUser(), datasource.getDsPwd())) {
            if (ObjectUtil.isNull(engine)) {
                return null;
            }
            return engine.getTableList(like).stream()
                    .sorted()
                    .toList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> fields(Long id, String tableName) {
        GenDatasource datasource = this.getById(id);
        try (AbstractDbEngine engine = DbEngineFactory.getEngine(datasource.getDsType(), datasource.getDsUrl(), datasource.getDsUser(), datasource.getDsPwd())) {
            if (ObjectUtil.isNull(engine)) {
                return null;
            }
            return engine.getTableField(tableName, datasource.getDbName());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String tableComment(Long id, String tableName) {
        GenDatasource datasource = this.getById(id);
        try (AbstractDbEngine engine = DbEngineFactory.getEngine(datasource.getDsType(), datasource.getDsUrl(), datasource.getDsUser(), datasource.getDsPwd())) {
            if (ObjectUtil.isNull(engine)) {
                return null;
            }
            return engine.getTableComment(tableName, datasource.getDbName());
        } catch (Exception e) {
            return null;
        }
    }
}
