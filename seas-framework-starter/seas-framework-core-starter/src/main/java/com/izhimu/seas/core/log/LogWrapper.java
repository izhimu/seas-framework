package com.izhimu.seas.core.log;

import com.izhimu.seas.core.pool.ThreadPoolFactory;
import com.izhimu.seas.core.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * 日志包装器
 *
 * @author haoran
 */
@SuppressWarnings("unused")
public class LogWrapper {

    /**
     * 日志输出线程池
     */
    private static final ExecutorService LOG_POOL = ThreadPoolFactory.build("Log");

    private static final String BOX_1 = "{}";
    private static final String BOX_2 = "{} {}";

    /**
     * 日志输出
     */
    private final Logger innerLog;
    /**
     * 是否异步
     */
    private final boolean async;

    public LogWrapper(Logger log, boolean async) {
        this.innerLog = log;
        this.async = async;
    }

    public LogWrapper(Class<?> clazz, boolean async) {
        this.innerLog = LoggerFactory.getLogger(clazz);
        this.async = async;
    }

    public LogWrapper(String name, boolean async) {
        this.innerLog = LoggerFactory.getLogger(name);
        this.async = async;
    }

    public static LogWrapper build(Logger log) {
        return new LogWrapper(log, true);
    }

    public static LogWrapper build() {
        String name = Thread.currentThread().getStackTrace()[2].getClassName();
        return new LogWrapper(name, true);
    }

    public static LogWrapper buildSync(Logger log) {
        return new LogWrapper(log, false);
    }

    public static LogWrapper buildSync() {
        String name = Thread.currentThread().getStackTrace()[2].getClassName();
        return new LogWrapper(name, false);
    }

    private void execute(Runnable runnable) {
        if (async) {
            String name = Thread.currentThread().getName();
            LOG_POOL.execute(() -> {
                Thread.currentThread().setName(name);
                runnable.run();
            });
        } else {
            runnable.run();
        }
    }

    private String tag(String tag) {
        return "[".concat(tag).concat("]");
    }

    private String param(Map<String, Object> param) {
        return JsonUtil.toJsonStr(param);
    }

    private String format(String msg, Object... params) {
        return Objects.equals(0, params.length) ? msg : MessageFormatter.basicArrayFormat(msg, params);
    }

    public void info(String msg, Object... params) {
        execute(() -> innerLog.info(format(msg, params)));
    }

    public void infoT(String tag, String msg, Object... params) {
        execute(() -> innerLog.info(BOX_2, tag(tag), format(msg, params)));
    }

    public void debug(String msg, Object... params) {
        if (!innerLog.isDebugEnabled()) {
            return;
        }
        execute(() -> innerLog.debug(format(msg, params)));
    }

    public void debugT(String tag, String msg, Object... params) {
        if (!innerLog.isDebugEnabled()) {
            return;
        }
        execute(() -> innerLog.debug(BOX_2, tag(tag), format(msg, params)));
    }

    public void warn(String msg, Object... params) {
        execute(() -> innerLog.warn(format(msg, params)));
    }

    public void warnT(String tag, String msg, Object... params) {
        execute(() -> innerLog.warn(BOX_2, tag(tag), format(msg, params)));
    }

    public void error(String msg, Object... params) {
        execute(() -> innerLog.error(format(msg, params)));
    }

    public void errorT(String tag, String msg, Object... params) {
        execute(() -> innerLog.error(BOX_2, tag(tag), format(msg, params)));
    }

    public void error(Throwable e) {
        execute(() -> innerLog.error("", e));
    }

    public void error(String msg, Throwable e, Object... params) {
        execute(() -> innerLog.error(format(msg, params), e));
    }

    public void errorT(String tag, String msg, Throwable e, Object... params) {
        execute(() -> innerLog.error(BOX_2, tag(tag), format(msg, params), e));
    }
}
