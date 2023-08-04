package com.izhimu.seas.data.handler;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JSON数据类型转换器
 *
 * @author haoran
 * @version v1.0
 */
@MappedTypes(String.class)
public class JsonTypeHandler extends BaseTypeHandler<String> {

    private static final PGobject JSON_OBJECT = new PGobject();
    private static final String JSON_TYPE = "json";

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        JSON_OBJECT.setType(JSON_TYPE);
        JSON_OBJECT.setValue(s);
        preparedStatement.setObject(i, JSON_OBJECT);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String sqlJson = resultSet.getString(s);
        if (StrUtil.isNotBlank(sqlJson)) {
            return sqlJson;
        }
        return null;
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String sqlJson = resultSet.getString(i);
        if (StrUtil.isNotBlank(sqlJson)) {
            return sqlJson;
        }
        return null;
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String sqlJson = callableStatement.getString(i);
        if (StrUtil.isNotBlank(sqlJson)) {
            return sqlJson;
        }
        return null;
    }
}
