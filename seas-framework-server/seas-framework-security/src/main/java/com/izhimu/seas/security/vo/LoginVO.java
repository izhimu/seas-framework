package com.izhimu.seas.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录视图层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class LoginVO implements Serializable {

    /**
     * token值
     */
    private String token;
    /**
     * 登录账号ID
     */
    private Long userId;
    /**
     * 剩余有效期（秒）
     */
    private Long tokenTimeout;
    /**
     * 密码过期
     */
    private Boolean pwdExpired;
    /**
     * 用户名
     */
    private String userName;
}
