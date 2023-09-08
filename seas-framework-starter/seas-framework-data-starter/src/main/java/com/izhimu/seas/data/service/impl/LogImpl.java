package com.izhimu.seas.data.service.impl;


import com.izhimu.seas.core.utils.LogUtil;
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

    public LogImpl(String clazz) {
        this.log = LoggerFactory.getLogger(clazz);
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
        log.error(LogUtil.format("Mybatis", s), e);
    }

    @Override
    public void error(String s) {
        log.error(LogUtil.format("Mybatis", s));
    }

    @Override
    public void debug(String s) {
        log.debug(LogUtil.format("Mybatis", s));
    }

    @Override
    public void trace(String s) {
        log.trace(LogUtil.format("Mybatis", s));
    }

    @Override
    public void warn(String s) {
        log.warn(LogUtil.format("Mybatis", s));
    }
}
