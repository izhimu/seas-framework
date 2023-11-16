package com.izhimu.seas.generate.db.engine;

import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.generate.db.enums.DbType;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 数据库引擎工厂类
 *
 * @author Haoran
 * @version v1.0
 */
@Slf4j
public class DbEngineFactory {

    private DbEngineFactory() {
    }

    /**
     * 根据给定的数据库信息获取数据库引擎实例
     *
     * @param type 数据库类型
     * @param url  数据库连接地址
     * @param user 用户名
     * @param pwd  密码
     * @return 数据库引擎实例
     */
    public static AbstractDbEngine getEngine(String type, String url, String user, String pwd) {
        DbType dbType = DbType.valueOf(type);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(dbType.getDriver());
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pwd);

        // 创建 JDBC 模板实例
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            return dbType.getEngine().getDeclaredConstructor(JdbcTemplate.class).newInstance(jdbcTemplate);
        } catch (Exception e) {
            log.error(LogUtil.format("DbEngine", "get engine error"), e);
            return null;
        }
    }

}
