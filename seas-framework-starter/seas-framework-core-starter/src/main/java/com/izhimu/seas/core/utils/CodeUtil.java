package com.izhimu.seas.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.experimental.UtilityClass;

/**
 * 编号工具类
 *
 * @author haoran
 * @version v1.0
 */
@UtilityClass
public class CodeUtil {

    /**
     * 生成树型编号
     *
     * @param lastCode 上一个编号
     * @return 树型编号
     */
    public static String generateTreeCode(String lastCode) {
        if (StrUtil.isEmptyIfStr(lastCode)) {
            return "";
        }
        long num = Long.parseLong(lastCode);
        String next = String.valueOf(num + 1);
        return "0".repeat(lastCode.length() - next.length()).concat(next);
    }
}
