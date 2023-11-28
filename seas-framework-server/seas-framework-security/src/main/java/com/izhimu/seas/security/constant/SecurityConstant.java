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

    public static final String TOKEN_NAME = "X-Auth-Token";

    /**
     * 默认用户
     */
    public static final String DEF_USER = "admin";

    /**
     * 秘钥超时时间
     * 默认600秒
     */
    public static final long ENCRYPT_EXPIRE = 120L;

    /**
     * 密码错误次数
     */
    public static final String LOGIN_ERR_NUM_KEY = "security:login.err";
    /**
     * 密码错误锁定用户
     */
    public static final String LOGIN_ERR_USER_KEY = "security:login.err.user";
}
