package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.BasAuthMenu;
import com.izhimu.seas.base.entity.BasAuthOrg;
import com.izhimu.seas.base.entity.BasRole;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

/**
 * 用户角色服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasRoleService extends IBaseService<BasRole> {

    /**
     * 更新角色菜单关联
     *
     * @param menu SysRoleMenu
     */
    void updateRoleMenu(BasAuthMenu menu);

    /**
     * 获取角色菜单关联
     *
     * @param roleId 角色ID
     * @return List<String>
     */
    List<String> getRoleMenu(Long roleId);

    /**
     * 更新角色组织关联
     *
     * @param org BasAuthOrg
     */
    void updateRoleOrg(BasAuthOrg org);

    /**
     * 获取角色组织关联
     *
     * @param roleId 角色ID
     * @return List<String>
     */
    List<String> getRoleOrg(Long roleId);

    /**
     * 更新用户角色关联
     *
     * @param entity SysUserRole
     */
    void updateUserRole(BasUserRole entity);

    /**
     * 获取用户角色关联
     *
     * @param roleId 角色ID
     * @return List<String>
     */
    List<String> getUserRole(Long roleId);

    /**
     * 获取选项
     *
     * @return Select
     */
    List<Select<Long>> select();
}
