package com.izhimu.seas.base.utils;

import cn.hutool.http.HttpUtil;
import com.izhimu.seas.core.utils.JsonUtil;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Ip地址工具
 *
 * @author haoran
 * @version v1.0
 */
public class IpUtil {

    private static final String API_URL = "https://opendata.baidu.com/api.php?query=${ip}&resource_id=6006&oe=utf8";
    private static final String LOCATION = "location";
    private static final String ZERO = "0";

    private IpUtil() {
    }

    /**
     * 根据IP地址获取地理位置
     *
     * @param ip IP地址
     * @return 地理位置
     */
    public static String getLocation(String ip) {
        String result = HttpUtil.get(API_URL.replace("${ip}", ip));
        Result resultObj = JsonUtil.toObject(result, Result.class);
        if (Objects.isNull(resultObj)) {
            return "";
        }
        if (!Objects.equals(ZERO, resultObj.getStatus())) {
            return "";
        }
        if (resultObj.getData().isEmpty()) {
            return "本地局域网";
        }
        Map<String, Object> dataObj = resultObj.getData().get(0);
        if (!dataObj.containsKey(LOCATION)) {
            return "";
        }
        return (String) dataObj.get(LOCATION);
    }

    @Data
    static class Result {
        private String status;
        private List<Map<String, Object>> data;
    }
}
