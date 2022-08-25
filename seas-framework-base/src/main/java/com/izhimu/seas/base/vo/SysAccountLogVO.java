package com.izhimu.seas.base.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志视图层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysAccountLogVO implements Serializable {

    /**
     * id
     */
    private Long id;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
    /**
     * 登录设备ID
     */
    private Long loginDeviceId;
    /**
     * 登录设备名称
     */
    private String loginDevice;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 登录地址
     */
    private String loginAddress;
    /**
     * 登录版本
     */
    private String loginVersion;
    /**
     * 系统版本
     */
    private String loginOsVersion;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 账号ID
     */
    private Long accountId;
    /**
     * 账号
     */
    private String account;
    /**
     * 状态
     * 0.登录，1.退出，2.密码错误，3.多次重试，4.禁用，5.登录失败
     */
    private Integer status;
}
