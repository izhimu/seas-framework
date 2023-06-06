package com.izhimu.seas.core.entity;

import com.izhimu.seas.core.annotation.ViewIgnore;
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
public class Login implements Serializable {

    /**
     * 账号
     */
    @ViewIgnore
    private String account;
    /**
     * 密码
     */
    @ViewIgnore
    private String password;
    /**
     * 密码加密key
     */
    @ViewIgnore
    private String passwordKey;
    /**
     * 验证码
     */
    @ViewIgnore
    private String verifyCode;
    /**
     * 验证码key
     */
    @ViewIgnore
    private String verifyCodeKey;
    /**
     * 设备类型
     */
    @ViewIgnore
    private DeviceType deviceType;
    /**
     * 设备号
     */
    @ViewIgnore
    private String deviceId;
    /**
     * 设备名称
     */
    @ViewIgnore
    private String deviceName;
    /**
     * 时间戳
     */
    @ViewIgnore
    private Long timestamp;
    /**
     * 版本号
     */
    @ViewIgnore
    private String version;
    /**
     * 系统版本
     */
    @ViewIgnore
    private String systemVersion;
    /**
     * 登录IP
     */
    @ViewIgnore
    private String ip;
    /**
     * 状态
     */
    @ViewIgnore
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
}
