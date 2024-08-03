package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.base.entity.BasTopicMenu;
import com.izhimu.seas.base.mapper.BasTopicMenuMapper;
import com.izhimu.seas.base.service.BasTopicMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * 主题菜单关联表服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasTopicMenuServiceImpl extends BaseServiceImpl<BasTopicMenuMapper, BasTopicMenu> implements BasTopicMenuService {
    @Override
    public boolean removeByTopicId(Serializable topicId) {
        return this.lambdaUpdate()
                .eq(BasTopicMenu::getTopicId, topicId)
                .remove();
    }

    @Override
    public List<Long> findMenuIdsByTopicId(Long topicId) {
        return this.lambdaQuery()
                .select(BasTopicMenu::getMenuId)
                .eq(BasTopicMenu::getTopicId, topicId)
                .eq(BasTopicMenu::getIsChecked, 1)
                .list()
                .stream()
                .map(BasTopicMenu::getMenuId)
                .toList();
    }
}
