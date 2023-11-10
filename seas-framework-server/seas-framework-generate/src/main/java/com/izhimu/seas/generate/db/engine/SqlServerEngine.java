package com.izhimu.seas.generate.db.engine;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.core.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * SqlServer数据库引擎
 *
 * @author Haoran
 * @version v1.0
 */
@Slf4j
public class SqlServerEngine extends AbstractDbEngine {

    SqlServerEngine(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询库中所有表
     *
     * @param like 查询条件
     * @return 数据集合
     */
    @Override
    public List<String> getTableList(String like) {
        StringBuilder sql = new StringBuilder("select name from sysobjects where xtype='U'");
        if (Objects.nonNull(like) && StrUtil.isNotBlank(like.trim())) {
            sql.append(" AND name ");
            sql.append("LIKE ").append("'%");
            sql.append(like).append("%'");
        }
        List<String> tableList = new ArrayList<>();
        try {
            tableList = mapToList(this.jdbcTemplate.queryForList(sql.toString()));
        } catch (Exception e) {
            log.error(LogUtil.format("DbEngine", "sqlserver get tables error"), e);
        }
        return tableList;
    }

    /**
     * 查询表中所有字段属性
     *
     * @return 数据集合
     */
    @Override
    public List<Map<String, Object>> getTableField(String tableName, String databaseName) {
        String sql = "SELECT COLUMN_NAME = a.name,IS_PK = CASE WHEN EXISTS (SELECT 1 FROM sysobjects WHERE xtype = 'PK' AND parent_obj = a.id AND name IN (SELECT name FROM sysindexes WHERE indid IN ( SELECT indid FROM sysindexkeys WHERE id = a.id AND colid = a.colid ))) THEN 'Y' ELSE 'N' END,COLUMN_TYPE = b.name,DATA_LENGTH = COLUMNPROPERTY( a.id, a.name, 'PRECISION' ),IS_NULL = CASE WHEN a.isnullable= 1 THEN 'Y' ELSE 'N' END,COLUMN_COMMENT = isnull( g.[value], '' ) FROM syscolumns a LEFT JOIN systypes b ON a.xusertype= b.xusertype INNER JOIN sysobjects d ON a.id= d.id AND d.xtype= 'U' AND d.name<> 'dtproperties' LEFT JOIN syscomments e ON a.cdefault= e.id LEFT JOIN sys.extended_properties g ON a.id= G.major_id AND a.colid= g.minor_id LEFT JOIN sys.extended_properties f ON d.id= f.major_id AND f.minor_id= 0 WHERE d.name= '" + tableName + "' ORDER BY a.id,a.colorder";
        List<Map<String, Object>> fieldList = new ArrayList<>();
        try {
            fieldList = this.jdbcTemplate.queryForList(sql);
            sqlServerPkFormat(fieldList);
            nullFormat(fieldList);
        } catch (Exception e) {
            log.error(LogUtil.format("DbEngine", "sqlserver get table field error"), e);
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
        String sql = "SELECT DISTINCT f.value FROM syscolumns a LEFT JOIN systypes b ON a.xusertype= b.xusertype INNER JOIN sysobjects d ON a.id= d.id AND d.xtype= 'U' AND d.name<> 'dtproperties' LEFT JOIN syscomments e ON a.cdefault= e.id LEFT JOIN sys.extended_properties g ON a.id= G.major_id AND a.colid= g.minor_id LEFT JOIN sys.extended_properties f ON d.id= f.major_id AND f.minor_id= 0 WHERE d.name= '" + tableName + "'";
        String comment = "";
        try {
            List<Map<String, Object>> fieldList = this.jdbcTemplate.queryForList(sql);
            if (!fieldList.isEmpty()) {
                comment = (String) fieldList.get(0).get("value");
            }
        } catch (Exception e) {
            log.error(LogUtil.format("DbEngine", "sqlserver get table comment error"), e);
        }
        return comment;
    }

    /**
     * MySQL是否主键统一格式化
     *
     * @param fieldList 字段集合
     */
    private void sqlServerPkFormat(List<Map<String, Object>> fieldList) {
        fieldList.forEach(map -> {
            String isPk = Convert.toStr(map.get("IS_PK"));
            if ("Y".equals(isPk)) {
                map.put("IS_PK", true);
            }
            if ("N".equals(isPk)) {
                map.put("IS_PK", false);
            }
        });
    }
}
