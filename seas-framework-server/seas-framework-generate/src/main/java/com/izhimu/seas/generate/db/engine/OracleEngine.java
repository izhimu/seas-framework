package com.izhimu.seas.generate.db.engine;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * Oracle数据库引擎
 *
 * @author Haoran
 * @version v1.0
 */
public class OracleEngine extends AbstractDbEngine {

    private static final String DATA_PRECISION = "DATA_PRECISION";
    private static final String DATA_SCALE = "DATA_SCALE";

    OracleEngine(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean testDatabase(String name) throws DbEngineException {
        List<String> databaseList = mapToList(this.jdbcTemplate.queryForList("SELECT NAME FROM v$database WHERE NAME = '" + name + "'"));
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
        StringBuilder sql = new StringBuilder("SELECT table_name FROM user_tables");
        if (Objects.nonNull(like) && CharSequenceUtil.isNotBlank(like.trim())) {
            sql.append(" WHERE table_name LIKE ").append("'%");
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
            log.error(e);
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
        List<Map<String, Object>> fieldList = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT COLUMN_NAME,COMMENTS AS COLUMN_COMMENT FROM all_col_comments WHERE TABLE_NAME = ");
            sql.append("'").append(tableName.toUpperCase()).append("'");
            sql.append("AND OWNER = ");
            sql.append("'").append(databaseName).append("'");
            List<Map<String, Object>> fieldList1 = this.jdbcTemplate.queryForList(sql.toString());
            sql.delete(0, sql.length());
            sql.append("SELECT COLUMN_NAME,DATA_TYPE AS COLUMN_TYPE,NULLABLE AS IS_NULL,DATA_LENGTH,DATA_PRECISION,DATA_SCALE FROM all_tab_columns WHERE TABLE_NAME = ");
            sql.append("'").append(tableName.toUpperCase()).append("'");
            sql.append("AND OWNER = ");
            sql.append("'").append(databaseName).append("'");
            fieldList = this.jdbcTemplate.queryForList(sql.toString());
            sql.delete(0, sql.length());
            sql.append("SELECT COLUMN_NAME FROM user_cons_columns a,user_constraints b WHERE a.constraint_name = b.constraint_name AND b.constraint_type = 'P' AND a.TABLE_NAME = ");
            sql.append("'").append(tableName.toUpperCase()).append("'");
            List<Map<String, Object>> fieldList2 = this.jdbcTemplate.queryForList(sql.toString());
            oraclePkFormat(fieldList, fieldList2);
            oracleDataType(fieldList);
            mapMerge(fieldList1, fieldList);
            nullFormat(fieldList);
            toLowerCase(fieldList);
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
        String sql = "SELECT COMMENTS FROM all_tab_comments WHERE OWNER = '" + databaseName + "' AND TABLE_NAME = '" + tableName.toUpperCase() + "'";
        String comment = "";
        try {
            List<Map<String, Object>> fieldList = this.jdbcTemplate.queryForList(sql);
            if (!fieldList.isEmpty()) {
                comment = (String) fieldList.getFirst().get("COMMENTS");
            }
        } catch (Exception e) {
            log.error(e);
        }
        return comment;
    }

    /**
     * 合并查询结果
     *
     * @param comment 注释
     * @param attr    字段属性
     */
    private void mapMerge(List<Map<String, Object>> comment, List<Map<String, Object>> attr) {
        for (Map<String, Object> map1 : attr) {
            for (Map<String, Object> map2 : comment) {
                if (map1.get(COLUMN_NAME).equals(map2.get(COLUMN_NAME))) {
                    map1.put(COLUMN_COMMENT, map2.get(COLUMN_COMMENT));
                    comment.remove(map2);
                    break;
                }
            }
        }
    }

    /**
     * Oracle是否主键统一格式化
     *
     * @param fieldList 字段集合
     */
    private void oraclePkFormat(List<Map<String, Object>> fieldList, List<Map<String, Object>> pkList) {
        fieldList.forEach(map1 -> {
            if (pkList.isEmpty()) {
                map1.put(IS_PK, false);
            }
            pkList.forEach(map2 -> map1.put(IS_PK, map1.get(COLUMN_NAME).equals(map2.get(COLUMN_NAME))));
        });
    }

    /**
     * Oracle数据类型处理
     *
     * @param fieldList 字段集合
     */
    private void oracleDataType(List<Map<String, Object>> fieldList) {
        fieldList.forEach(map -> {
            if (Objects.nonNull(map.get(DATA_PRECISION))) {
                StringBuilder length = new StringBuilder();
                length.append(map.get(DATA_PRECISION));
                if (Objects.nonNull(map.get(DATA_SCALE))) {
                    length.append(",").append(map.get(DATA_SCALE));
                }
                map.put("DATA_LENGTH", length);
            }
            map.remove(DATA_PRECISION);
            map.remove(DATA_SCALE);
        });
    }

    /**
     * 字段名转小写
     *
     * @param fieldList 字段集合
     */
    private void toLowerCase(List<Map<String, Object>> fieldList) {
        fieldList.forEach(field -> field.put(COLUMN_NAME, Convert.toStr(field.get(COLUMN_NAME)).toLowerCase()));
    }
}
