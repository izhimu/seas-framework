package com.izhimu.seas.security.constant;

/**
 * 安全相关常量
 *
 * @author haoran
 * @version v1.0
 */
public class SecurityConstant {

    private SecurityConstant() {
    }

    /**
     * 默认用户
     */
    public static final String DEF_USER = "admin";

    /**
     * 登录请求地址
     */
    public static final String URL_LOGIN = "/login";

    /**
     * 登出请求地址
     */
    public static final String URL_LOGOUT = "/logout";

    /**
     * 秘钥超时时间
     * 默认600秒
     */
    public static final long ENCRYPT_EXPIRE = 600L;

    /**
     * 密码错误次数
     */
    public static final String LOGIN_ERR_NUM_KEY = "security:login.err";
}
