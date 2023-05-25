package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.enums.SearchType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 登录日志实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_SYS_ACCOUNT_LOG")
public class SysAccountLog extends IdEntity {

    /**
     * 登录时间
     */
    @OrderBy(asc = false)
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
    @Search
    private String loginIp;
    /**
     * 登录地址
     */
    @Search
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
    @Search(type = SearchType.EQUALS)
    private Long userId;
    /**
     * 账号ID
     */
    @Search(type = SearchType.EQUALS)
    private Long accountId;
    /**
     * 状态
     * 0.登录，1.退出，2.密码错误，3.多次重试，4.禁用，5.登录失败
     */
    @Search(type = SearchType.EQUALS)
    private Integer status;
    // Param
    /**
     * 登录时间
     */
    @ViewIgnore
    @TableField(exist = false)
    @Search(name = "login_time", type = SearchType.GE)
    private LocalDateTime loginTimeStart;
    /**
     * 登录时间
     */
    @ViewIgnore
    @TableField(exist = false)
    @Search(name = "login_time", type = SearchType.LE)
    private LocalDateTime loginTimeEnd;
    // View
    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;
    /**
     * 账号
     */
    @TableField(exist = false)
    private String account;
}
