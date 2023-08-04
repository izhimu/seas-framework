package com.izhimu.seas.core.scan;

import com.izhimu.seas.core.server.IServer;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

/**
 * 包扫描服务
 *
 * @author haoran
 * @version 1.0
 */
@SuppressWarnings("unused")
@Slf4j
public class ScanServer implements IServer {

    private final String path;

    public ScanServer(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        log.info("开始包扫描 -> path:{}", path);
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage(path))
                .addScanners(Scanners.TypesAnnotated);
        Reflections reflections = new Reflections(configurationBuilder);
        ScanHandlerHolder.all().forEach(v -> {
            log.info(v.name().concat(" 开始 -->"));
            v.scan(reflections);
            log.info(v.name().concat(" 结束 <--"));
        });
        log.info("包扫描结束 <-");
    }
}
