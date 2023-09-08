package com.izhimu.seas.base.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.izhimu.seas.base.entity.BasMenu;
import com.izhimu.seas.base.mapper.BasMenuMapper;
import com.izhimu.seas.base.service.BasMenuService;
import com.izhimu.seas.core.entity.RefreshSession;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.event.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.security.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 菜单服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasMenuServiceImpl extends BaseServiceImpl<BasMenuMapper, BasMenu> implements BasMenuService {

    @Override
    public boolean updateById(BasMenu entity) {
        boolean b = super.updateById(entity);
        EventManager.trigger(CoreEvent.E_SESSION_REFRESH, RefreshSession.menu(entity.getId()));
        return b;
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean b = super.removeById(id);
        EventManager.trigger(CoreEvent.E_SESSION_REFRESH, RefreshSession.menu(id));
        return b;
    }

    @Override
    public List<Tree<Long>> tree(BasMenu param) {
        List<BasMenu> list = this.paramQuery().param(param).orderBy().wrapper().list();
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("key");
        config.setNameKey("label");
        return TreeUtil.build(list, 0L, config,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getMenuName());
                    tree.setWeight(treeNode.getSort());
                });
    }

    @Override
    public List<BasMenu> auth() {
        User user = SecurityUtil.getUser();
        if (Boolean.TRUE.equals(user.getIsSuper())) {
            return this.lambdaQuery().orderByAsc(BasMenu::getSort).list();
        }
        List<String> menuAuth = user.getMenuAuth();
        if (menuAuth.isEmpty()) {
            return Collections.emptyList();
        }
        return this.lambdaQuery()
                .in(BasMenu::getMenuCode, menuAuth)
                .orderByAsc(BasMenu::getSort)
                .list();
    }

    @Override
    public List<String> findMenuCodeByIds(Collection<Long> ids) {
        return this.lambdaQuery()
                .select(BasMenu::getMenuCode)
                .orderByAsc(BasMenu::getSort)
                .in(BasMenu::getId, ids)
                .isNotNull(BasMenu::getMenuCode)
                .list()
                .stream()
                .map(BasMenu::getMenuCode)
                .toList();
    }
}
