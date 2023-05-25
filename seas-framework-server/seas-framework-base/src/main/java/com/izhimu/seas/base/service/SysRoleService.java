package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.SysAuthMenu;
import com.izhimu.seas.base.entity.SysRole;
import com.izhimu.seas.base.entity.SysUserRole;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

/**
 * 用户角色服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysRoleService extends IBaseService<SysRole> {

    /**
     * 更新角色菜单关联
     *
     * @param menu SysRoleMenu
     */
    void updateRoleMenu(SysAuthMenu menu);

    /**
     * 获取角色菜单关联
     *
     * @param roleId 角色ID
     * @return List<String>
     */
    List<String> getRoleMenu(Long roleId);

    /**
     * 更新用户角色关联
     *
     * @param entity SysUserRole
     */
    void updateUserRole(SysUserRole entity);

    /**
     * 获取用户角色关联
     *
     * @param roleId 角色ID
     * @return List<String>
     */
    List<String> getUserRole(Long roleId);
}
