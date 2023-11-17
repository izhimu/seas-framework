package com.izhimu.seas.core.utils;

import cn.hutool.http.HttpUtil;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * Ip地址工具
 *
 * @author haoran
 * @version v1.0
 */
@UtilityClass
public class IpUtil {

    private static final String API_URL = "https://api.mir6.com/api/ip?ip=${ip}&type=json";
    private static final Integer SUCCESS = 200;
    private static final String BLANK = " ";

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
        if (!Objects.equals(SUCCESS, resultObj.getCode())) {
            return "";
        }
        ResultData data = resultObj.getData();
        return data.getCountry()
                .concat(data.getProvince())
                .concat(data.getCity())
                .concat(BLANK)
                .concat(data.getIsp())
                .concat(data.getNet());
    }

    @Data
    static class Result {
        private Integer code;
        private ResultData data;
    }

    @Data
    static class ResultData {
        private String country;
        private String province;
        private String city;
        private String isp;
        private String net;
    }
}
