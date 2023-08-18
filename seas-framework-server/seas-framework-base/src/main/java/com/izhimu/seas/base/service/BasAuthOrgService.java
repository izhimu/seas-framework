package com.izhimu.seas.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.base.entity.BasAuthOrg;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 组织权限服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasAuthOrgService extends IService<BasAuthOrg> {

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色ID
     * @return boolean
     */
    boolean removeByRoleId(Serializable roleId);

    /**
     * 根据角色ID查询组织ID集合
     *
     * @param roleId 角色ID
     * @return 组织ID集合
     */
    List<Long> findOrgIdByRoleId(Long roleId);

    /**
     * 根据角色ID查询组织ID集合
     *
     * @param roleId 角色ID
     * @return 组织ID集合
     */
    Set<Long> findOrgIdByRoleIdDistinct(Long roleId);

    /**
     * 根据组织ID查询角色ID集合
     *
     * @param orgId 组织ID
     * @return 角色ID集合
     */
    Set<Long> findRoleIdByOrgIdDistinct(Long orgId);
}
