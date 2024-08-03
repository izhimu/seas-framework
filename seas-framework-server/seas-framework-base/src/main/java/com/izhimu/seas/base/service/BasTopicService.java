package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.BasTopicMenu;
import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.base.entity.BasTopic;

import java.util.List;

/**
 * 主题表服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasTopicService extends IBaseService<BasTopic> {

    /**
     * 更新主题菜单关联
     *
     * @param menu 主题菜单关联实体
     */
    void updateTopicMenu(BasTopicMenu menu);

    /**
     * 获取当前用户有权限的主题列表
     *
     * @return 主题列表
     */
    List<BasTopic> authTopicList();
}
