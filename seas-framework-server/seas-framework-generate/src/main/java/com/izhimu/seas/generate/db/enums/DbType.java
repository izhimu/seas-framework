package com.izhimu.seas.generate.db.enums;

import com.izhimu.seas.generate.db.engine.*;
import lombok.Getter;

/**
 * 数据库驱动枚举
 *
 * @author Haoran
 * @version v1.0
 */
@Getter
public enum DbType {

    /**
     * PostgreSQL
     */
    POSYTGRSQL("org.postgresql.Driver", PostgreEngine.class),
    /**
     * MySQL
     */
    MYSQL("com.mysql.cj.jdbc.Driver", MySqlEngine.class),
    /**
     * Oracle
     */
    ORACLE("oracle.jdbc.driver.OracleDriver", OracleEngine.class),
    /**
     * SqlServer
     */
    SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", SqlServerEngine.class);

    private final String driver;

    private final Class<? extends AbstractDbEngine> engine;

    DbType(String driver, Class<? extends AbstractDbEngine> engine) {
        this.driver = driver;
        this.engine = engine;
    }
}
