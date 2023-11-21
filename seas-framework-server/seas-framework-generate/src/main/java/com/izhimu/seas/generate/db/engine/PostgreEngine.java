package com.izhimu.seas.generate.db.engine;

import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import com.izhimu.seas.generate.util.TypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * MySql数据库引擎
 *
 * @author Haoran
 * @version v1.0
 */
@Slf4j
public class PostgreEngine extends AbstractDbEngine {

    PostgreEngine(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean testDatabase(String name) throws DbEngineException {
        List<String> databaseList = mapToList(this.jdbcTemplate.queryForList("SELECT DATNAME FROM PG_DATABASE WHERE DATNAME = '" + name + "'"));
        return !databaseList.isEmpty();
    }

    /**
     * 查询库中所有表
     *
     * @param like 查询条件
     * @return 数据集合
     */
    @Override
    public List<String> getTableList(String like) {
        StringBuilder sql = new StringBuilder("SELECT tablename FROM pg_tables WHERE schemaname NOT IN ('pg_catalog','information_schema')");
        if (Objects.nonNull(like) && StrUtil.isNotBlank(like.trim())) {
            sql.append(" AND tablename LIKE ").append("'%");
            sql.append(like).append("%'");
        }
        List<String> resultTable = new ArrayList<>();
        try {
            List<String> tableList = mapToList(this.jdbcTemplate.queryForList(sql.toString()));
            resultTable = new ArrayList<>();
            for (String tableName : tableList) {
                resultTable.add(tableName.toLowerCase());
            }
        } catch (Exception e) {
            log.error(LogUtil.format("DbEngine", "postgres get tables error"), e);
        }
        return resultTable;
    }

    /**
     * 查询表中所有字段属性
     *
     * @return 数据集合
     */
    @Override
    public List<Map<String, Object>> getTableField(String tableName, String databaseName) {
        String sql = "SELECT A.attname COLUMN_NAME,D.description COLUMN_COMMENT,T.typname AS COLUMN_TYPE,(case when a.attnotnull = true then true else false end) as IS_NULL,(case when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1] = attnum and contype = 'p') > 0 then true else false end) as IS_PK FROM pg_class C,pg_attribute A,pg_type T,pg_description D WHERE A.attnum > 0 AND A.attrelid = C.oid AND A.atttypid = T.oid AND D.objoid = A.attrelid AND D.objsubid = A.attnum AND C.relname = '" + tableName + "'";
        List<Map<String, Object>> fieldList = new ArrayList<>();
        try {
            fieldList = this.jdbcTemplate.queryForList(sql);
            nullFormat(fieldList);
        } catch (Exception e) {
            log.error(LogUtil.format("DbEngine", "postgres get table field error"), e);
        }
        return fieldList;
    }

    /**
     * 查询表注释
     *
     * @return 注释
     */
    @Override
    public String getTableComment(String tableName, String databaseName) {
        String sql = "SELECT cast(obj_description(relfilenode,'pg_class') as varchar) as comment FROM pg_class c WHERE relname = '" + tableName + "'";
        String comment = "";
        try {
            List<Map<String, Object>> fieldList = this.jdbcTemplate.queryForList(sql);
            if (!fieldList.isEmpty()) {
                comment = (String) fieldList.get(0).get("comment");
            }
        } catch (Exception e) {
            log.error(LogUtil.format("DbEngine", "postgres get table comment error"), e);
        }
        return comment;
    }
}
