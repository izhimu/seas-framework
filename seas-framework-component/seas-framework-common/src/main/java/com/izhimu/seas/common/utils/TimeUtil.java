package com.izhimu.seas.common.utils;

import lombok.experimental.UtilityClass;

/**
 * 时间工具类
 *
 * @author Haoran
 * @version v1.0
 * @date 2021/11/29 15:31
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
        return (d == 0 ? "" : String.valueOf(d).concat("天"))
                .concat(h == 0 ? "" : String.valueOf(h).concat("小时"))
                .concat(m == 0 ? "" : String.valueOf(m).concat("分钟"))
                .concat(s == 0 ? "" : String.valueOf(s).concat("秒"));
    }

    /**
     * 毫秒转计时字符串
     *
     * @param milliseconds 毫秒
     * @return 计时字符串
     */
    public static String millisecondsToStr(long milliseconds) {
        long seconds = milliseconds / 1000;
        return seconds == 0 ? String.valueOf(milliseconds).concat("毫秒") : secondToStr(seconds);
    }
}
