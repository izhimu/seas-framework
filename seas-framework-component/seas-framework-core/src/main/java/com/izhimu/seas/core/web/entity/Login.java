package com.izhimu.seas.core.web.entity;

import com.izhimu.seas.core.annotation.View;
import com.izhimu.seas.core.enums.DeviceType;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录数据传输层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class Login implements Serializable, IView {

    /**
     * 账号
     */
    @View(ignore = true)
    private String account;
    /**
     * 密码
     */
    @View(ignore = true)
    private String password;
    /**
     * 密码加密key
     */
    @View(ignore = true)
    private String passwordKey;
    /**
     * 验证码
     */
    @View(ignore = true)
    private String verifyCode;
    /**
     * 验证码key
     */
    @View(ignore = true)
    private String verifyCodeKey;
    /**
     * 设备类型
     */
    @View(ignore = true)
    private DeviceType deviceType;
    /**
     * 设备号
     */
    @View(ignore = true)
    private String deviceId;
    /**
     * 设备名称
     */
    @View(ignore = true)
    private String deviceName;
    /**
     * 时间戳
     */
    @View(ignore = true)
    private Long timestamp;
    /**
     * 版本号
     */
    @View(ignore = true)
    private String version;
    /**
     * 系统版本
     */
    @View(ignore = true)
    private String systemVersion;
    /**
     * 登录IP
     */
    @View(ignore = true)
    private String ip;
    /**
     * 状态
     */
    @View(ignore = true)
    private Integer status;
    // View
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

    private boolean pwdExpiredb;
}
