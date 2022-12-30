package com.izhimu.seas.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户账号传输层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysAccountDTO implements Serializable {

    private Long id;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userCertificate;
    /**
     * 加密Key
     */
    private String passwordKey;
}
