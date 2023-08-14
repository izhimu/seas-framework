package com.izhimu.seas.base.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.base.entity.BasAuthMenu;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 菜单权限服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasAuthMenuService extends IService<BasAuthMenu> {

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色ID
     * @return boolean
     */
    boolean removeByRoleId(Serializable roleId);

    /**
     * 根据角色ID查询菜单ID集合
     *
     * @param roleId 角色ID
     * @return 菜单ID集合
     */
    List<Long> findMenuIdByRoleId(Long roleId);

    /**
     * 根据角色ID集合查询菜单ID集合
     *
     * @param roleIds 角色ID集合
     * @return 角色ID集合
     */
    Set<Long> findMenuIdByRoleIdDistinct(Collection<Long> roleIds);
}
