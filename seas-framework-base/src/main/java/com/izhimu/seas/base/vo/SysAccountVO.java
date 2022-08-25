package com.izhimu.seas.base.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户账号实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysAccountVO implements Serializable {

    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userAccount;
    /**
     * 用户类型
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
}
