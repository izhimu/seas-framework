package com.izhimu.seas.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.base.entity.BasUserRole;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户角色中间服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasUserRoleService extends IService<BasUserRole> {

    /**
     * 根据角色ID删除
     *
     * @param roleId 角色ID
     * @return boolean
     */
    boolean removeByRoleId(Serializable roleId);

    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return boolean
     */
    boolean removeByUserId(Serializable userId);

    /**
     * 根据角色ID查询用户ID集合
     *
     * @param roleId 角色ID
     * @return 用户ID集合
     */
    List<Long> findUserIdByRoleId(Long roleId);

    /**
     * 根据角色ID查询用户ID集合
     *
     * @param roleId 角色ID
     * @return 用户ID集合
     */
    Set<Long> findUserIdByRoleIdDistinct(Long roleId);

    /**
     * 根据用户ID查询角色ID集合
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    List<Long> findRoleIdByUserId(Long userId);

    /**
     * 根据用户ID查询角色ID集合
     *
     * @param userId 用户ID
     * @return 角色ID集合
     */
    Set<Long> findRoleIdByUserIdDistinct(Long userId);
}
