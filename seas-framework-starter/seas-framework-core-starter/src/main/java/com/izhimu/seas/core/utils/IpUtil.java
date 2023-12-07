package com.izhimu.seas.core.utils;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.HttpUtil;
import jakarta.servlet.http.HttpServletRequest;
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

    /**
     * 获取客户端IP
     *
     * @param request          请求对象{@link HttpServletRequest}
     * @param otherHeaderNames 其他自定义头文件，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtil.addAll(headers, otherHeaderNames);
        }

        return getClientIPByHeader(request, headers);
    }

    /**
     * 获取客户端IP
     *
     * @param request     请求对象{@link HttpServletRequest}
     * @param headerNames 自定义头，通常在Http服务器（例如Nginx）中配置
     * @return IP地址
     */
    public static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
        String ip;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddr();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }
}
