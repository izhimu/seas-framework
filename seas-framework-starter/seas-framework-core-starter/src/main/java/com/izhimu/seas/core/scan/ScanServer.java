package com.izhimu.seas.core.scan;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.core.server.IServer;
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
public class ScanServer implements IServer {

    private static final LogWrapper log = LogWrapper.buildSync("ScanServer");

    private final String path;

    public ScanServer(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        log.info("Path {}", path);
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage(path))
                .addScanners(Scanners.TypesAnnotated);
        Reflections reflections = new Reflections(configurationBuilder);
        TimeInterval timer = DateUtil.timer();
        ScanHandlerHolder.all().forEach(v -> {
            log.info("Scanning");
            v.scan(reflections);
            log.info("Done {}ms", timer.interval());
        });
    }
}
