package com.izhimu.seas.generate.db.engine;

import cn.hutool.core.convert.Convert;
import com.izhimu.seas.core.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据库引擎
 *
 * @author Haoran
 * @version v1.0
 */
@Slf4j
public abstract class AbstractDbEngine {

    /**
     * JDBC模板
     */
    JdbcTemplate jdbcTemplate;

    /**
     * 测试连接
     *
     * @return 是否连接
     */
    public boolean testConnection() {
        DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource == null) {
            return false;
        }
        try (Connection conn = dataSource.getConnection()) {
            return !conn.isClosed();
        } catch (SQLException e) {
            log.error(LogUtil.format("DbEngine", "test connection error"), e);
            return false;
        }
    }

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
                .collect(Collectors.toList());
    }

    /**
     * 是否为空统一格式化
     *
     * @param fieldList 字段集合
     */
    void nullFormat(List<Map<String, Object>> fieldList) {
        fieldList.forEach(map -> {
            String isNull = Convert.toStr(map.get("IS_NULL"));
            if ("Y".equals(isNull) || "YES".equals(isNull)) {
                map.put("IS_NULL", true);
            }
            if ("N".equals(isNull) || "NO".equals(isNull)) {
                map.put("IS_NULL", false);
            }
        });
    }
}
