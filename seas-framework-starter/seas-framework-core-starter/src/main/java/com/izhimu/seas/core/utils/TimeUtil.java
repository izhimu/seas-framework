package com.izhimu.seas.core.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * 时间工具类
 *
 * @author Haoran
 * @version v1.0
 */
@UtilityClass
public class TimeUtil {

    /**
     * 秒转计时字符串
     *
     * @param second 秒
     * @return 计时字符串
     */
    public static String secondToStr(long second) {
        long d = second / 86400;
        long h = second % 86400 / 3600;
        long m = second % 86400 % 3600 / 60;
        long s = second % 86400 % 3600 % 60;
        return (Objects.equals(0, d) ? "" : String.valueOf(d).concat("天"))
                .concat(Objects.equals(0, h) ? "" : String.valueOf(h).concat("小时"))
                .concat(Objects.equals(0, m) ? "" : String.valueOf(m).concat("分钟"))
                .concat(Objects.equals(0, s) ? "" : String.valueOf(s).concat("秒"));
    }

    /**
     * 毫秒转计时字符串
     *
     * @param milliseconds 毫秒
     * @return 计时字符串
     */
    public static String millisecondsToStr(long milliseconds) {
        long seconds = milliseconds / 1000;
        return Objects.equals(0, seconds) ? String.valueOf(milliseconds).concat("毫秒") : secondToStr(seconds);
    }
}
