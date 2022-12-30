package com.izhimu.seas.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色关联传输层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysUserRoleDTO implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 用户列表
     */
    private List<Long> userIds;
}
