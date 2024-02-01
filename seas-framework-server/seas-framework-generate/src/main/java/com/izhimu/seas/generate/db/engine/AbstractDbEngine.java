package com.izhimu.seas.generate.db.engine;

import cn.hutool.core.convert.Convert;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 数据库引擎
 *
 * @author Haoran
 * @version v1.0
 */
public abstract class AbstractDbEngine implements AutoCloseable {

    public static final String COLUMN_NAME = "COLUMN_NAME";
    public static final String COLUMN_COMMENT = "COLUMN_COMMENT";
    public static final String COLUMN_TYPE = "COLUMN_TYPE";
    public static final String IS_PK = "IS_PK";
    public static final String IS_NULL = "IS_NULL";

    /**
     * JDBC模板
     */
    JdbcTemplate jdbcTemplate;

    /**
     * 测试连接
     *
     * @return 是否连接
     */
    public boolean testConnection() throws DbEngineException {
        DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource == null) {
            return false;
        }
        try (Connection conn = dataSource.getConnection()) {
            return !conn.isClosed();
        } catch (SQLException e) {
            log.error(e);
            throw new DbEngineException(e.getMessage());
        }
    }

    /**
     * 校验数据库名
     *
     * @param name String
     * @return boolean
     * @throws DbEngineException DbEngineException
     */
    public abstract boolean testDatabase(String name) throws DbEngineException;

    /**
     * 查询库中所有表
     *
     * @param like 查询条件
     * @return 数据集合
     */
    public abstract List<String> getTableList(String like);

    /**
     * 查询表中所有字段属性
     *
     * @param tableName    表名
     * @param databaseName 数据库名
     * @return 数据集合
     */
    public abstract List<Map<String, Object>> getTableField(String tableName, String databaseName);

    /**
     * 查询表注释
     *
     * @param tableName    表名
     * @param databaseName 数据库名
     * @return 注释
     */
    public abstract String getTableComment(String tableName, String databaseName);

    /**
     * map转换list
     *
     * @param maps 数据Map
     * @return 数据List
     */
    List<String> mapToList(List<Map<String, Object>> maps) {
        return maps.stream()
                .flatMap(v -> v.values().stream())
                .map(Convert::toStr)
                .toList();
    }

    /**
     * 是否为空统一格式化
     *
     * @param fieldList 字段集合
     */
    void nullFormat(List<Map<String, Object>> fieldList) {
        fieldList.forEach(map -> {
            String isNull = Convert.toStr(map.get(IS_NULL));
            if ("Y".equals(isNull) || "YES".equals(isNull)) {
                map.put(IS_NULL, true);
            }
            if ("N".equals(isNull) || "NO".equals(isNull)) {
                map.put(IS_NULL, false);
            }
        });
    }

    @Override
    public void close() {
        if (Objects.isNull(jdbcTemplate)) {
            return;
        }
        DataSource dataSource = jdbcTemplate.getDataSource();
        if (Objects.isNull(dataSource)) {
            return;
        }
        if (dataSource instanceof HikariDataSource hikariDataSource) {
            hikariDataSource.close();
        }
    }
}
