package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.base.entity.BasAuthTopic;
import com.izhimu.seas.base.entity.BasTopicMenu;
import com.izhimu.seas.base.service.BasAuthTopicService;
import com.izhimu.seas.base.service.BasTopicMenuService;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.base.entity.BasTopic;
import com.izhimu.seas.base.mapper.BasTopicMapper;
import com.izhimu.seas.base.service.BasTopicService;
import com.izhimu.seas.security.util.SecurityUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 主题表服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasTopicServiceImpl extends BaseServiceImpl<BasTopicMapper, BasTopic> implements BasTopicService {

    @Resource
    private BasAuthTopicService authTopicService;
    @Resource
    private BasTopicMenuService topicMenuService;

    @Override
    public void updateTopicMenu(BasTopicMenu entity) {
        topicMenuService.removeByTopicId(entity.getTopicId());
        List<Long> menuIds = entity.getMenuIds();
        if (Objects.nonNull(menuIds)) {
            topicMenuService.saveBatch(menuIds.stream()
                    .map(v -> {
                        BasTopicMenu topicMenu = new BasTopicMenu();
                        topicMenu.setTopicId(entity.getTopicId());
                        topicMenu.setMenuId(v);
                        topicMenu.setIsChecked(1);
                        return topicMenu;
                    }).toList());
        }
        List<Long> menuPIds = entity.getMenuPIds();
        if (Objects.nonNull(menuPIds)) {
            topicMenuService.saveBatch(menuPIds.stream()
                    .map(v -> {
                        BasTopicMenu topicMenu = new BasTopicMenu();
                        topicMenu.setTopicId(entity.getTopicId());
                        topicMenu.setMenuId(v);
                        topicMenu.setIsChecked(0);
                        return topicMenu;
                    }).toList());
        }
    }

    @Override
    public List<BasTopic> authTopicList() {
        User user = SecurityUtil.getUser();
        if (user.getIsSuper()) {
            return list();
        }
        Set<Long> roleIds = user.getRoleIds();
        Set<Long> topicIds = authTopicService.lambdaQuery()
                .in(BasAuthTopic::getRoleId, roleIds)
                .list()
                .stream()
                .map(BasAuthTopic::getTopicId)
                .collect(Collectors.toSet());
        return this.lambdaQuery()
                .eq(BasTopic::getId, topicIds)
                .list()
                .stream()
                .peek(v -> v.setMenuIds(topicMenuService.lambdaQuery()
                        .eq(BasTopicMenu::getTopicId, v.getId())
                        .list()
                        .stream()
                        .map(BasTopicMenu::getMenuId)
                        .toList()))
                .toList();
    }
}
