package com.izhimu.seas.base.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.izhimu.seas.base.entity.BasAuthMenu;
import com.izhimu.seas.base.entity.BasMenu;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.base.mapper.BasMenuMapper;
import com.izhimu.seas.base.service.BasAuthMenuService;
import com.izhimu.seas.base.service.BasMenuService;
import com.izhimu.seas.base.service.BasUserRoleService;
import com.izhimu.seas.base.service.BasUserService;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasMenuServiceImpl extends BaseServiceImpl<BasMenuMapper, BasMenu> implements BasMenuService {

    @Resource
    private BasUserService userService;

    @Resource
    private BasUserRoleService userRoleService;

    @Resource
    private BasAuthMenuService authMenuService;

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
        User user = userService.getCurrentUser();
        if (Boolean.TRUE.equals(user.getIsSuper())) {
            return this.lambdaQuery().orderByAsc(BasMenu::getSort).list();
        }
        List<BasMenu> all = this.lambdaQuery().eq(BasMenu::getDisplay, 1).list();
        Map<Long, BasMenu> allMap = all.stream().collect(Collectors.toMap(BasMenu::getId, v -> v));
        Set<Long> roles = userRoleService.lambdaQuery()
                .select(BasUserRole::getRoleId)
                .eq(BasUserRole::getUserId, user.getId())
                .list()
                .stream()
                .map(BasUserRole::getRoleId)
                .collect(Collectors.toSet());
        if (roles.isEmpty()) {
            return List.of();
        }
        Set<Long> menus = authMenuService.lambdaQuery()
                .select(BasAuthMenu::getMenuId)
                .in(BasAuthMenu::getRoleId, roles)
                .list()
                .stream()
                .map(BasAuthMenu::getMenuId)
                .collect(Collectors.toSet());
        if (menus.isEmpty()) {
            return List.of();
        }
        Set<BasMenu> result = new HashSet<>();
        menus.forEach(v -> {
            result.add(allMap.get(v));
            getAuthMenuIds(result, allMap, v);
        });
        return result.stream().sorted(Comparator.comparing(BasMenu::getSort)).collect(Collectors.toList());
    }

    /**
     * 获取权限菜单
     *
     * @param menuIds 权限菜单
     * @param all     所有菜单
     * @param id      权限ID
     */
    private void getAuthMenuIds(Set<BasMenu> menuIds, Map<Long, BasMenu> all, Long id) {
        if (all.containsKey(id)) {
            BasMenu basMenu = all.get(id);
            menuIds.add(basMenu);
            getAuthMenuIds(menuIds, all, basMenu.getParentId());
        }
    }
}
