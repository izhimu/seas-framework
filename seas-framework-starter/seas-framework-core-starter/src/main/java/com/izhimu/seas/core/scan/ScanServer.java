package com.izhimu.seas.core.scan;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.izhimu.seas.core.server.IServer;
import com.izhimu.seas.core.utils.LogUtil;
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
        log.info(LogUtil.format("ScanServer", "Path {}"), path);
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .addUrls(ClasspathHelper.forPackage(path))
                .addScanners(Scanners.TypesAnnotated);
        Reflections reflections = new Reflections(configurationBuilder);
        TimeInterval timer = DateUtil.timer();
        ScanHandlerHolder.all().forEach(v -> {
            log.info(LogUtil.format("ScanServer", v.name(), "Scanning"));
            v.scan(reflections);
            log.info(LogUtil.format("ScanServer", v.name(), "Done {}ms"), timer.interval());
        });
    }
}
