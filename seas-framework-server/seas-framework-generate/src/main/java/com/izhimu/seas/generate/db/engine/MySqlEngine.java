package com.izhimu.seas.generate.db.engine;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.generate.db.exception.DbEngineException;
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
public class MySqlEngine extends AbstractDbEngine {

    private static final LogWrapper log = LogWrapper.build("MySqlEngine");

    MySqlEngine(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean testDatabase(String name) throws DbEngineException {
        List<String> databaseList = mapToList(this.jdbcTemplate.queryForList("SHOW DATABASES"));
        return databaseList.contains(name);
    }

    /**
     * 查询库中所有表
     *
     * @param like 查询条件
     * @return 数据集合
     */
    @Override
    public List<String> getTableList(String like) {
        List<String> result = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SHOW TABLES ");
        if (Objects.nonNull(like) && CharSequenceUtil.isNotBlank(like.trim())) {
            sql.append("LIKE ").append("'%").append(like).append("%'");
        }
        try {
            result = mapToList(this.jdbcTemplate.queryForList(sql.toString()));
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 查询表中所有字段属性
     *
     * @return 数据集合
     */
    @Override
    public List<Map<String, Object>> getTableField(String tableName, String databaseName) {
        String sql = "SELECT COLUMN_NAME,COLUMN_TYPE,COLUMN_COMMENT,IS_NULLABLE AS IS_NULL,COLUMN_KEY AS IS_PK FROM information_schema.columns WHERE TABLE_NAME = '{}' AND TABLE_SCHEMA = '{}'";
        sql = CharSequenceUtil.format(sql, tableName, databaseName);
        List<Map<String, Object>> fieldList = new ArrayList<>();
        try {
            fieldList = this.jdbcTemplate.queryForList(sql);
            mysqlDataLength(fieldList);
            mysqlPkFormat(fieldList);
            nullFormat(fieldList);
        } catch (Exception e) {
            log.error(e);
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
        String sql = "SELECT table_comment FROM information_schema.TABLES WHERE TABLE_NAME = '{}' AND TABLE_SCHEMA = '{}'";
        sql = CharSequenceUtil.format(sql, tableName, databaseName);
        String comment = "";
        try {
            List<Map<String, Object>> fieldList = this.jdbcTemplate.queryForList(sql);
            if (!fieldList.isEmpty()) {
                comment = Convert.toStr(fieldList.getFirst().get("table_comment"));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return comment;
    }

    /**
     * MySQL数据长度处理
     *
     * @param fieldList 字段集合
     */
    private void mysqlDataLength(List<Map<String, Object>> fieldList) {
        fieldList.forEach(map -> {
            String dataType = Convert.toStr(map.get(COLUMN_TYPE));
            String[] temp = dataType.split("\\(");
            if (temp.length > 1) {
                temp = temp[1].split("\\)");
                temp = temp[0].split(",");
                map.put("DATA_LENGTH", temp[0]);
            } else {
                map.put("DATA_LENGTH", 0);
            }
        });
    }

    /**
     * MySQL是否主键统一格式化
     *
     * @param fieldList 字段集合
     */
    private void mysqlPkFormat(List<Map<String, Object>> fieldList) {
        fieldList.forEach(map -> map.put(IS_PK, "PRI".equals(Convert.toStr(map.get(IS_PK)))));
    }
}
