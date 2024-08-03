package com.izhimu.seas.base.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.base.entity.BasAuthTopic;

import java.io.Serializable;
import java.util.List;

/**
 * 角色主题关联表服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasAuthTopicService extends IBaseService<BasAuthTopic> {

    /**
     * 根据角色ID删除主题关联关系
     *
     * @param roleId 角色ID
     * @return 删除结果
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean removeByRoleId(Serializable roleId);

    /**
     * 更新角色主题关联关系
     *
     * @param entity 角色主题关联关系实体
     * @return 更新结果
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean updateAuthTopic(BasAuthTopic entity);

    /**
     * 根据角色ID获取主题ID列表
     *
     * @param roleId 角色ID
     * @return 主题ID列表
     */
    List<Long> getTopicIdsByRoleId(Serializable roleId);
}
