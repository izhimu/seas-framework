package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单权限实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_AUTH_MENU")
public class SysAuthMenu implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 角色ID
     */
    private Long roleId;
}
