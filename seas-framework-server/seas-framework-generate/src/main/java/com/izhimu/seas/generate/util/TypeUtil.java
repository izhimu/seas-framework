package com.izhimu.seas.generate.util;

import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.generate.db.enums.DbType;
import lombok.experimental.UtilityClass;

import java.util.List;

import static com.izhimu.seas.generate.constant.TypeConst.*;

/**
 * 类型转换工具
 *
 * @author haoran
 */
@UtilityClass
public class TypeUtil {

    public static String getJavaType(String type, DbType dbType) {
        return switch (dbType) {
            case POSYTGRSQL -> getJavaTypeByPostgres(type);
            case MYSQL -> getJavaTypeByMysql(type);
            case ORACLE -> getJavaTypeByOracle(type);
            case SQLSERVER -> getJavaTypeBySqlserver(type);
        };
    }

    public static String getJavaTypeByPostgres(String type) {
        return switch (type.toLowerCase()) {
            case DB_TYPE_BOOL -> JAVA_TYPE_BOOLEAN;
            case DB_TYPE_NUMERIC -> JAVA_TYPE_BIG_DECIMAL;
            case DB_TYPE_DOUBLE_PRECISION -> JAVA_TYPE_DOUBLE;
            case DB_TYPE_FLOAT4, DB_TYPE_FLOAT8 -> JAVA_TYPE_FLOAT;
            case DB_TYPE_INT2, DB_TYPE_INT4 -> JAVA_TYPE_INTEGER;
            case DB_TYPE_INT8 -> JAVA_TYPE_LONG;
            case DB_TYPE_DATE -> JAVA_TYPE_LOCAL_DATE;
            case DB_TYPE_TIMESTAMP -> JAVA_TYPE_LOCAL_DATETIME;
            case DB_TYPE_TIME -> JAVA_TYPE_LOCAL_TIME;
            case DB_TYPE_CHAR, DB_TYPE_JSON, DB_TYPE_TEXT, DB_TYPE_VARCHAR -> JAVA_TYPE_STRING;
            default -> JAVA_TYPE_OBJECT;
        };
    }

    public static String getJavaTypeByMysql(String type) {
        type = fmt(type);
        return switch (type.toLowerCase()) {
            case DB_TYPE_BOOL, DB_TYPE_BOOLEAN -> JAVA_TYPE_BOOLEAN;
            case DB_TYPE_DECIMAL, DB_TYPE_NUMBERIC -> JAVA_TYPE_BIG_DECIMAL;
            case DB_TYPE_DOUBLE -> JAVA_TYPE_DOUBLE;
            case DB_TYPE_FLOAT -> JAVA_TYPE_FLOAT;
            case DB_TYPE_BIT, DB_TYPE_TINYINT, DB_TYPE_SMALLINT, DB_TYPE_MEDIUMINT, DB_TYPE_INT, DB_TYPE_INTEGER ->
                    JAVA_TYPE_INTEGER;
            case DB_TYPE_BIGINT -> JAVA_TYPE_LONG;
            case DB_TYPE_DATE -> JAVA_TYPE_LOCAL_DATE;
            case DB_TYPE_DATETIME, DB_TYPE_TIMESTAMP -> JAVA_TYPE_LOCAL_DATETIME;
            case DB_TYPE_TIME -> JAVA_TYPE_LOCAL_TIME;
            case DB_TYPE_CHAR, DB_TYPE_TEXT, DB_TYPE_VARCHAR -> JAVA_TYPE_STRING;
            default -> JAVA_TYPE_OBJECT;
        };
    }

    public static String getJavaTypeByOracle(String type) {
        type = fmt(type);
        return switch (type.toLowerCase()) {
            case DB_TYPE_DECIMAL -> JAVA_TYPE_BIG_DECIMAL;
            case DB_TYPE_NUMBER, DB_TYPE_BINARY_DOUBLE -> JAVA_TYPE_DOUBLE;
            case DB_TYPE_FLOAT, DB_TYPE_REAL, DB_TYPE_BINARY_FLOAT -> JAVA_TYPE_FLOAT;
            case DB_TYPE_INTEGER, DB_TYPE_INT, DB_TYPE_SMALLINT -> JAVA_TYPE_INTEGER;
            case DB_TYPE_DATE, DB_TYPE_TIMESTAMP -> JAVA_TYPE_LOCAL_DATETIME;
            case DB_TYPE_CHAR, DB_TYPE_NCHAR, DB_TYPE_VARCHAR2, DB_TYPE_NVARCHAR2 -> JAVA_TYPE_STRING;
            default -> JAVA_TYPE_OBJECT;
        };
    }

    public static String getJavaTypeBySqlserver(String type) {
        type = fmt(type);
        return switch (type.toLowerCase()) {
            case DB_TYPE_BIT -> JAVA_TYPE_BOOLEAN;
            case DB_TYPE_DECIMAL, DB_TYPE_NUMERIC -> JAVA_TYPE_BIG_DECIMAL;
            case DB_TYPE_FLOAT -> JAVA_TYPE_DOUBLE;
            case DB_TYPE_REAL -> JAVA_TYPE_FLOAT;
            case DB_TYPE_TINYINT, DB_TYPE_SMALLINT, DB_TYPE_INT -> JAVA_TYPE_INTEGER;
            case DB_TYPE_BIGINT -> JAVA_TYPE_LONG;
            case DB_TYPE_DATE -> JAVA_TYPE_LOCAL_DATE;
            case DB_TYPE_DATETIME, DB_TYPE_DATETIME2, DB_TYPE_TIMESTAMP -> JAVA_TYPE_LOCAL_DATETIME;
            case DB_TYPE_TIME -> JAVA_TYPE_LOCAL_TIME;
            case DB_TYPE_CHAR, DB_TYPE_VARCHAR, DB_TYPE_NCHAR, DB_TYPE_NVARCHAR, DB_TYPE_TEXT, DB_TYPE_NTEXT ->
                    JAVA_TYPE_STRING;
            default -> JAVA_TYPE_OBJECT;
        };
    }

    public static String getJsType(String type, DbType dbType) {
        return switch (dbType) {
            case POSYTGRSQL -> getJsTypeByPostgres(type);
            case MYSQL -> getJsTypeByMysql(type);
            case ORACLE -> getJsTypeByOracle(type);
            case SQLSERVER -> getJsTypeBySqlserver(type);
        };
    }

    public static String getJsTypeByPostgres(String type) {
        return switch (type.toLowerCase()) {
            case DB_TYPE_BOOL -> JS_TYPE_BOOLEAN;
            case DB_TYPE_NUMERIC, DB_TYPE_DOUBLE_PRECISION, DB_TYPE_FLOAT4, DB_TYPE_FLOAT8, DB_TYPE_INT2, DB_TYPE_INT4 ->
                    JS_TYPE_NUMBER;
            case DB_TYPE_CHAR, DB_TYPE_JSON, DB_TYPE_TEXT, DB_TYPE_VARCHAR, DB_TYPE_INT8, DB_TYPE_DATE, DB_TYPE_TIME, DB_TYPE_TIMESTAMP ->
                    JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }

    public static String getJsTypeByMysql(String type) {
        type = fmt(type);
        return switch (type.toLowerCase()) {
            case DB_TYPE_BOOL, DB_TYPE_BOOLEAN -> JS_TYPE_BOOLEAN;
            case DB_TYPE_BIT, DB_TYPE_TINYINT, DB_TYPE_SMALLINT, DB_TYPE_MEDIUMINT, DB_TYPE_INT, DB_TYPE_INTEGER, DB_TYPE_DECIMAL, DB_TYPE_NUMBERIC, DB_TYPE_DOUBLE, DB_TYPE_FLOAT ->
                    JS_TYPE_NUMBER;
            case DB_TYPE_CHAR, DB_TYPE_TEXT, DB_TYPE_VARCHAR, DB_TYPE_BIGINT, DB_TYPE_DATE, DB_TYPE_DATETIME, DB_TYPE_TIMESTAMP, DB_TYPE_TIME ->
                    JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }

    public static String getJsTypeByOracle(String type) {
        type = fmt(type);
        return switch (type.toLowerCase()) {
            case DB_TYPE_DECIMAL, DB_TYPE_NUMBER, DB_TYPE_BINARY_DOUBLE, DB_TYPE_FLOAT, DB_TYPE_REAL, DB_TYPE_BINARY_FLOAT, DB_TYPE_INTEGER, DB_TYPE_INT, DB_TYPE_SMALLINT ->
                    JS_TYPE_NUMBER;
            case DB_TYPE_CHAR, DB_TYPE_NCHAR, DB_TYPE_VARCHAR2, DB_TYPE_NVARCHAR2, DB_TYPE_DATE, DB_TYPE_TIMESTAMP ->
                    JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }

    public static String getJsTypeBySqlserver(String type) {
        type = fmt(type);
        return switch (type.toLowerCase()) {
            case DB_TYPE_BIT -> JS_TYPE_BOOLEAN;
            case DB_TYPE_DECIMAL, DB_TYPE_NUMERIC, DB_TYPE_FLOAT, DB_TYPE_REAL, DB_TYPE_TINYINT, DB_TYPE_SMALLINT, DB_TYPE_INT ->
                    JS_TYPE_NUMBER;
            case DB_TYPE_CHAR, DB_TYPE_VARCHAR, DB_TYPE_NCHAR, DB_TYPE_NVARCHAR, DB_TYPE_TEXT, DB_TYPE_NTEXT, DB_TYPE_BIGINT, DB_TYPE_DATE, DB_TYPE_DATETIME, DB_TYPE_DATETIME2, DB_TYPE_TIMESTAMP, DB_TYPE_TIME ->
                    JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }

    public List<String> getJavaImport(List<String> typeList) {
        return typeList.stream()
                .distinct()
                .map(v -> switch (v) {
                    case JAVA_TYPE_BIG_DECIMAL -> JAVA_IMPORT_BIG_DECIMAL;
                    case JAVA_TYPE_LOCAL_DATE -> JAVA_IMPORT_LOCAL_DATE;
                    case JAVA_TYPE_LOCAL_DATETIME -> JAVA_IMPORT_LOCAL_DATETIME;
                    case JAVA_TYPE_LOCAL_TIME -> JAVA_IMPORT_LOCAL_TIME;
                    default -> null;
                })
                .filter(StrUtil::isNotBlank)
                .toList();
    }

    private static String fmt(String type) {
        int sub = type.indexOf("(");
        if (sub > 0) {
            type = type.substring(0, sub);
        }
        return type;
    }
}
