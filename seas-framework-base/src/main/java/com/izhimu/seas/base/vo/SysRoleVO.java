package com.izhimu.seas.base.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户角色视图层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysRoleVO implements Serializable {

    /**
     * ID
     */
    private Long id;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
}
