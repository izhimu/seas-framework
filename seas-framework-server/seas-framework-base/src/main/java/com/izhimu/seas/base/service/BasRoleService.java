package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.BasAuthMenu;
import com.izhimu.seas.base.entity.BasAuthOrg;
import com.izhimu.seas.base.entity.BasRole;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.core.entity.DataPermission;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.data.service.IBaseService;

import java.util.Collection;
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
     * 更新角色组织关联
     *
     * @param org BasAuthOrg
     */
    void updateRoleOrg(BasAuthOrg org);

    /**
     * 更新用户角色关联
     *
     * @param entity SysUserRole
     */
    void updateUserRole(BasUserRole entity);

    /**
     * 获取选项
     *
     * @return Select
     */
    List<Select<Long>> select();

    /**
     * 根据用户ID获取菜单权限
     *
     * @param user 用户ID
     * @return 菜单权限
     */
    List<String> findMenuAuthByUserId(User user);

    /**
     * 根据用户ID获取数据权限
     *
     * @param user 用户ID
     * @return 数据权限
     */
    DataPermission getDataPermissionByUserId(User user);

    /**
     * 根据ID集合查询名称集合
     *
     * @param ids ID集合
     * @return 名称集合
     */
    List<String> findNameById(Collection<Long> ids);
}
