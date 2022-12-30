package com.izhimu.seas.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 用户传输层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysUserDTO implements Serializable {

    private Long id;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户性别
     * 0.保密 1.男 2.女
     */
    private Integer userSex;
    /**
     * 用户生日
     */
    private LocalDate birthday;
    /**
     * 个人签名
     */
    private String signature;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private Long face;
    /**
     * 小头像
     */
    private Long faceSmall;
    /**
     * 状态
     * 0.正常 1.禁用
     */
    private Integer status;
    /**
     * 账号
     */
    private List<SysAccountDTO> accounts;
}

