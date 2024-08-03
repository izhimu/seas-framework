package com.izhimu.seas.base.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.base.entity.BasTopicMenu;

import java.io.Serializable;
import java.util.List;

/**
 * 主题菜单关联表服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasTopicMenuService extends IBaseService<BasTopicMenu> {

    /**
     * 根据主题ID删除关联关系
     *
     * @param topicId 主题ID
     * @return 删除成功返回true，否则返回false
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean removeByTopicId(Serializable topicId);

    /**
     * 根据主题ID查询菜单ID列表
     *
     * @param topicId 主题ID
     * @return 菜单ID列表
     */
    List<Long> findMenuIdsByTopicId(Long topicId);
}
