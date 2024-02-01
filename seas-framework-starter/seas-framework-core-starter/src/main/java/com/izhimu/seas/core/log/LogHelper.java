package com.izhimu.seas.core.log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日志帮助程序
 *
 * @author haoran
 */
public class LogHelper {

    private static final Map<String, LogWrapper> WRAPPER_MAP = new ConcurrentHashMap<>();

    public static final LogHelper log = new LogHelper();

    private static LogWrapper getLog() {
        String name = Thread.currentThread().getStackTrace()[3].getClassName();
        return WRAPPER_MAP.computeIfAbsent(name, k -> new LogWrapper(k, true));
    }

    public void info(String msg, Object... params) {
        getLog().info(msg, params);
    }

    public void infoT(String tag, String msg, Object... params) {
        getLog().infoT(tag, msg, params);
    }

    public void debug(String msg, Object... params) {
        getLog().debug(msg, params);
    }

    public void debugT(String tag, String msg, Object... params) {
        getLog().debugT(tag, msg, params);
    }

    public void warn(String msg, Object... params) {
        getLog().warn(msg, params);
    }

    public void warnT(String tag, String msg, Object... params) {
        getLog().warnT(tag, msg, params);
    }

    public void error(String msg, Object... params) {
        getLog().error(msg, params);
    }

    public void errorT(String tag, String msg, Object... params) {
        getLog().errorT(tag, msg, params);
    }

    public void error(Throwable e) {
        getLog().error(e);
    }

    public void error(String msg, Throwable e, Object... params) {
        getLog().error(msg, e, params);
    }

    public void errorT(String tag, String msg, Throwable e, Object... params) {
        getLog().errorT(tag, msg, e, params);
    }
}
