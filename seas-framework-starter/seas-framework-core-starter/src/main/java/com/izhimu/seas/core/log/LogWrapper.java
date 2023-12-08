package com.izhimu.seas.core.log;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.core.pool.ThreadPoolFactory;
import com.izhimu.seas.core.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
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
    private static final String BOX_3 = "{} {} {}";
    private static final String BOX_4 = "{} {} {} {}";

    /**
     * 日志输出
     */
    private final Logger log;
    /**
     * 主题
     */
    private final String topic;
    /**
     * 是否异步
     */
    private final boolean async;

    public LogWrapper(Logger log, String topic, boolean async) {
        this.log = log;
        this.topic = "•".concat(StrUtil.truncateUtf8(StrUtil.fillAfter(topic, ' ', 16), 16));
        this.async = async;
    }

    public LogWrapper(Class<?> clazz, String topic, boolean async) {
        this.log = LoggerFactory.getLogger(clazz);
        this.topic = "•".concat(StrUtil.truncateUtf8(StrUtil.fillAfter(topic, ' ', 16), 16));
        this.async = async;
    }

    public static LogWrapper build(Logger log, String topic) {
        return new LogWrapper(log, topic, true);
    }

    public static LogWrapper build(String topic) {
        Class<?> clazz = Thread.currentThread().getStackTrace()[1].getClass();
        return new LogWrapper(clazz, topic, true);
    }

    public static LogWrapper buildSync(Logger log, String topic) {
        return new LogWrapper(log, topic, false);
    }

    public static LogWrapper buildSync(String topic) {
        Class<?> clazz = Thread.currentThread().getStackTrace()[1].getClass();
        return new LogWrapper(clazz, topic, false);
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
        return "⟨".concat(StrUtil.truncateUtf8(StrUtil.fillAfter(tag, ' ', 16), 16)).concat("⟩");
    }

    private String param(Map<String, Object> param) {
        return JsonUtil.toJsonStr(param);
    }

    public void info(String msg, Object... params) {
        execute(() -> log.info(BOX_2, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void info(String tag, String msg, Object... params) {
        execute(() -> log.info(BOX_3, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void info(Map<String, Object> param, String msg, Object... params) {
        execute(() -> log.info(BOX_3, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void info(String tag, Map<String, Object> param, String msg, Object... params) {
        execute(() -> log.info(BOX_4, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void debug(String msg, Object... params) {
        if (!log.isDebugEnabled()) {
            return;
        }
        execute(() -> log.debug(BOX_2, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void debug(String tag, String msg, Object... params) {
        if (!log.isDebugEnabled()) {
            return;
        }
        execute(() -> log.debug(BOX_3, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void debug(Map<String, Object> param, String msg, Object... params) {
        if (!log.isDebugEnabled()) {
            return;
        }
        execute(() -> log.debug(BOX_3, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void debug(String tag, Map<String, Object> param, String msg, Object... params) {
        if (!log.isDebugEnabled()) {
            return;
        }
        execute(() -> log.debug(BOX_4, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void warn(String msg, Object... params) {
        execute(() -> log.warn(BOX_2, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void warn(String tag, String msg, Object... params) {
        execute(() -> log.warn(BOX_3, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void warn(Map<String, Object> param, String msg, Object... params) {
        execute(() -> log.warn(BOX_3, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void warn(String tag, Map<String, Object> param, String msg, Object... params) {
        execute(() -> log.warn(BOX_4, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void error(String msg, Object... params) {
        execute(() -> log.error(BOX_2, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void error(String tag, String msg, Object... params) {
        execute(() -> log.error(BOX_3, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params)));
    }

    public void error(Map<String, Object> param, String msg, Object... params) {
        execute(() -> log.error(BOX_3, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void error(String tag, Map<String, Object> param, String msg, Object... params) {
        execute(() -> log.error(BOX_4, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param)));
    }

    public void error(Throwable e) {
        execute(() -> log.error(BOX_1, topic, e));
    }

    public void error(String msg, Throwable e, Object... params) {
        execute(() -> log.error(BOX_2, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params), e));
    }

    public void error(String tag, String msg, Throwable e, Object... params) {
        execute(() -> log.error(BOX_3, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params), e));
    }

    public void error(Map<String, Object> param, String msg, Throwable e, Object... params) {
        execute(() -> log.error(BOX_3, topic, params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param), e));
    }

    public void error(String tag, Map<String, Object> param, String msg, Throwable e, Object... params) {
        execute(() -> log.error(BOX_4, topic, tag(tag), params.length == 0 ? msg : CharSequenceUtil.format(msg, params), param(param), e));
    }
}
