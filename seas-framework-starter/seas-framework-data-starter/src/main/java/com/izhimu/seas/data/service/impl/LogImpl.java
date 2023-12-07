package com.izhimu.seas.data.service.impl;

import com.izhimu.seas.core.log.LogWrapper;
import org.apache.ibatis.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义日志输出
 *
 * @author haoran
 * @version v1.0
 */
public class LogImpl implements Log {

    private final Logger log;
    private final LogWrapper logWrapper;

    public LogImpl(String clazz) {
        this.log = LoggerFactory.getLogger(clazz);
        this.logWrapper = LogWrapper.build(this.log, "Mybatis");
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void error(String s, Throwable e) {
        logWrapper.error(s, e);
    }

    @Override
    public void error(String s) {
        logWrapper.error(s);
    }

    @Override
    public void debug(String s) {
        logWrapper.debug(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        logWrapper.warn(s);
    }
}
