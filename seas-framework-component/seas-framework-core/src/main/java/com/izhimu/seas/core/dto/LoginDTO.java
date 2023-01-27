package com.izhimu.seas.core.dto;

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
public class LoginDTO implements Serializable {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码加密key
     */
    private String passwordKey;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 验证码key
     */
    private String verifyCodeKey;

    /**
     * 设备类型
     */
    private DeviceType deviceType;

    /**
     * 设备号
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 版本号
     */
    private String version;

    /**
     * 系统版本
     */
    private String systemVersion;

    /**
     * 登录IP
     */
    private String ip;
}
