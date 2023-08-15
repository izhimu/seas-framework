package com.izhimu.seas.core.config;

import cn.hutool.log.StaticLog;
import cn.hutool.setting.Setting;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author haoran
 * @version v1.0
 */
@Order(-100)
@Configuration
public class BannerConfig implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Setting setting = new Setting(".version");
        StaticLog.info("                           \n\n" +
                "__________________ ________\n" +
                "__  ___/  _ \\  __ `/_  ___/\n" +
                "_(__  )/  __/ /_/ /_(__  ) \n" +
                "/____/ \\___/\\__,_/ /____/  \n\n 🐋 " +
                setting.getStr("name", "Version", "seas-project") + " | v" +
                setting.getStr("ver", "Version", "0.0.0") + " | " +
                setting.getStr("time", "Version", "") + " \n");
    }
}
