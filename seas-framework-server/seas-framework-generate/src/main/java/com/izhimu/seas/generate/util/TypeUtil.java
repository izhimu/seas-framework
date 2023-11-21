package com.izhimu.seas.generate.util;

import com.izhimu.seas.generate.db.enums.DbType;
import lombok.experimental.UtilityClass;

/**
 * 类型转换工具
 *
 * @author haoran
 */
@UtilityClass
public class TypeUtil {

    public static final String JAVA_TYPE_BOOLEAN = "Boolean";
    public static final String JAVA_TYPE_BIG_DECIMAL = "BigDecimal";
    public static final String JAVA_TYPE_DOUBLE = "Double";
    public static final String JAVA_TYPE_FLOAT = "Float";
    public static final String JAVA_TYPE_INTEGER = "Integer";
    public static final String JAVA_TYPE_LONG = "Long";
    public static final String JAVA_TYPE_LOCAL_DATE = "LocalDate";
    public static final String JAVA_TYPE_LOCAL_DATETIME = "LocalDateTime";
    public static final String JAVA_TYPE_LOCAL_TIME = "LocalTime";
    public static final String JAVA_TYPE_OBJECT = "Object";
    public static final String JAVA_TYPE_STRING = "String";

    public static final String JS_TYPE_ANY = "any";
    public static final String JS_TYPE_BOOLEAN = "boolean";
    public static final String JS_TYPE_NUMBER = "number";
    public static final String JS_TYPE_STRING = "string";

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
            case "bool" -> JAVA_TYPE_BOOLEAN;
            case "numeric" -> JAVA_TYPE_BIG_DECIMAL;
            case "double precision" -> JAVA_TYPE_DOUBLE;
            case "float4", "float8" -> JAVA_TYPE_FLOAT;
            case "int2", "int4" -> JAVA_TYPE_INTEGER;
            case "int8" -> JAVA_TYPE_LONG;
            case "date" -> JAVA_TYPE_LOCAL_DATE;
            case "timestamp" -> JAVA_TYPE_LOCAL_DATETIME;
            case "time" -> JAVA_TYPE_LOCAL_TIME;
            case "char", "json", "text", "varchar" -> JAVA_TYPE_STRING;
            default -> JAVA_TYPE_OBJECT;
        };
    }

    public static String getJavaTypeByMysql(String type) {
        type = type.substring(0, type.indexOf("("));
        return switch (type.toLowerCase()) {
            case "bool", "boolean" -> JAVA_TYPE_BOOLEAN;
            case "decimal", "numberic" -> JAVA_TYPE_BIG_DECIMAL;
            case "double" -> JAVA_TYPE_DOUBLE;
            case "float" -> JAVA_TYPE_FLOAT;
            case "bit", "tinyint", "smallint", "mediumint", "int", "integer" -> JAVA_TYPE_INTEGER;
            case "bigint" -> JAVA_TYPE_LONG;
            case "date" -> JAVA_TYPE_LOCAL_DATE;
            case "datetime", "timestamp" -> JAVA_TYPE_LOCAL_DATETIME;
            case "time" -> JAVA_TYPE_LOCAL_TIME;
            case "char", "text", "varchar" -> JAVA_TYPE_STRING;
            default -> JAVA_TYPE_OBJECT;
        };
    }

    public static String getJavaTypeByOracle(String type) {
        type = type.substring(0, type.indexOf("("));
        return switch (type.toLowerCase()) {
            case "decimal" -> JAVA_TYPE_BIG_DECIMAL;
            case "number", "binary_double" -> JAVA_TYPE_DOUBLE;
            case "float", "real", "binary_float" -> JAVA_TYPE_FLOAT;
            case "integer", "int", "smallint" -> JAVA_TYPE_INTEGER;
            case "date", "timestamp" -> JAVA_TYPE_LOCAL_DATETIME;
            case "char", "nchar", "varchar2", "nvarchar2" -> JAVA_TYPE_STRING;
            default -> JAVA_TYPE_OBJECT;
        };
    }

    public static String getJavaTypeBySqlserver(String type) {
        type = type.substring(0, type.indexOf("("));
        return switch (type.toLowerCase()) {
            case "bit" -> JAVA_TYPE_BOOLEAN;
            case "decimal", "numeric" -> JAVA_TYPE_BIG_DECIMAL;
            case "float" -> JAVA_TYPE_DOUBLE;
            case "real" -> JAVA_TYPE_FLOAT;
            case "tinyint", "smallint", "int" -> JAVA_TYPE_INTEGER;
            case "bigint" -> JAVA_TYPE_LONG;
            case "date" -> JAVA_TYPE_LOCAL_DATE;
            case "datetime", "datetime2", "timestamp" -> JAVA_TYPE_LOCAL_DATETIME;
            case "time" -> JAVA_TYPE_LOCAL_TIME;
            case "char", "varchar", "nchar", "nvarchar", "text", "ntext" -> JAVA_TYPE_STRING;
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
            case "bool" -> JS_TYPE_BOOLEAN;
            case "numeric", "double precision", "float4", "float8", "int2", "int4" -> JS_TYPE_NUMBER;
            case "char", "json", "text", "varchar", "int8", "date", "time", "timestamp" -> JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }

    public static String getJsTypeByMysql(String type) {
        type = type.substring(0, type.indexOf("("));
        return switch (type.toLowerCase()) {
            case "bool", "boolean" -> JS_TYPE_BOOLEAN;
            case "bit", "tinyint", "smallint", "mediumint", "int", "integer", "decimal", "numberic", "double", "float" ->
                    JS_TYPE_NUMBER;
            case "char", "text", "varchar", "bigint", "date", "datetime", "timestamp", "time" -> JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }

    public static String getJsTypeByOracle(String type) {
        type = type.substring(0, type.indexOf("("));
        return switch (type.toLowerCase()) {
            case "decimal", "number", "binary_double", "float", "real", "binary_float", "integer", "int", "smallint" ->
                    JS_TYPE_NUMBER;
            case "char", "nchar", "varchar2", "nvarchar2", "date", "timestamp" -> JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }

    public static String getJsTypeBySqlserver(String type) {
        type = type.substring(0, type.indexOf("("));
        return switch (type.toLowerCase()) {
            case "bit" -> JS_TYPE_BOOLEAN;
            case "decimal", "numeric", "float", "real", "tinyint", "smallint", "int" -> JS_TYPE_NUMBER;
            case "char", "varchar", "nchar", "nvarchar", "text", "ntext", "bigint", "date", "datetime", "datetime2", "timestamp", "time" ->
                    JS_TYPE_STRING;
            default -> JS_TYPE_ANY;
        };
    }
}
