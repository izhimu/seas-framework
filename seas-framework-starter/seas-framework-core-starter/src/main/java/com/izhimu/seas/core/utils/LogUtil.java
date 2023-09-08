package com.izhimu.seas.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Objects;

/**
 * 日志工具
 *
 * @author haoran
 * @version v1.0
 */
@UtilityClass
public class LogUtil {

    /**
     * 标准格式化输出
     *
     * @param name  名称
     * @param tag   标签
     * @param msg   消息
     * @param param 参数
     * @return log
     */
    public static String format(String name, String tag, String msg, Map<String, Object> param) {
        String log = "(Seas) ".concat(StrUtil.truncateUtf8(StrUtil.fillAfter(name, ' ', 16), 16)).concat(" ");
        if (Objects.nonNull(tag)) {
            log = log.concat("[").concat(StrUtil.truncateUtf8(StrUtil.fillAfter(tag, ' ', 16), 16)).concat("] ");
        }
        if (Objects.nonNull(msg)) {
            log = log.concat(msg).concat(" ");
        }
        if (Objects.nonNull(param)) {
            String jsonStr = JsonUtil.toJsonStr(param);
            if (Objects.nonNull(jsonStr)) {
                log = log.concat(jsonStr);
            }
        }
        return log;
    }

    /**
     * 标准格式化输出
     *
     * @param name 名称
     * @param tag  标签
     * @param msg  消息
     * @return log
     */
    public static String format(String name, String tag, String msg) {
        return format(name, tag, msg, null);
    }

    /**
     * 标准格式化输出
     *
     * @param name  名称
     * @param msg   消息
     * @param param 参数
     * @return log
     */
    public static String format(String name, String msg, Map<String, Object> param) {
        return format(name, null, msg, param);
    }

    /**
     * 标准格式化输出
     *
     * @param name 名称
     * @param msg  消息
     * @return log
     */
    public static String format(String name, String msg) {
        return format(name, null, msg, null);
    }
}
