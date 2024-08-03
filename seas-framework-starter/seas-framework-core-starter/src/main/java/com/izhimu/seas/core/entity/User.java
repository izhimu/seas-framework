package com.izhimu.seas.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户信息
 *
 * @author 13346
 * @version v1.0
 */
@Data
public class User implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String nickName;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userCertificate;
    /**
     * 用户类型
     */
    private Integer typeCode;
    /**
     * 状态
     * 0、正常 1、锁定 2、密码过期
     */
    private Integer status;
    /**
     * 逻辑删除
     */
    private Integer logicDel;
    /**
     * 是否为超级管理员
     */
    private Boolean isSuper;
    /**
     * 登录信息
     */
    private Login login;
    /**
     * 所属组织ID
     */
    private Long orgId;
    /**
     * 菜单权限列表
     */
    private List<String> menuAuth;
    /**
     * 角色ID列表
     */
    private Set<Long> roleIds;
    /**
     * 数据权限列表
     */
    private DataPermission dataAuth;
}
