package com.izhimu.seas.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单关联传输层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysAuthMenuDTO implements Serializable {

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单列表
     */
    private List<Long> menuIds;
}
