package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.View;
import com.izhimu.seas.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户账号实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_SYS_ACCOUNT")
public class SysAccount extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userAccount;
    /**
     * 密码
     */
    @View(ignore = true)
    private String userCertificate;
    /**
     * 用户类型
     * 0、系统账号用户
     */
    private Integer typeCode;
    /**
     * 状态
     * 0、正常 1、过期 2、锁定 3、密码过期
     */
    private Integer status;
    /**
     * 最后登录信息ID
     */
    private Long lastId;
    // DTO
    /**
     * 加密Key
     */
    @View(ignore = true)
    @TableField(exist = false)
    private String passwordKey;
}
