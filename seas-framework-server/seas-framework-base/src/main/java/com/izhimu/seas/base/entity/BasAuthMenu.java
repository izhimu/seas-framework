package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜单权限实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_BAS_AUTH_MENU")
public class BasAuthMenu extends IdEntity {

    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 选中的，0否，1是
     */
    private Integer isChecked;
    // DTO
    /**
     * 菜单列表
     */
    @ViewIgnore
    @TableField(exist = false)
    private List<Long> menuIds;
    /**
     * 父菜单列表
     */
    @ViewIgnore
    @TableField(exist = false)
    private List<Long> menuPIds;
}
