package com.izhimu.seas.base.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.extra.cglib.CglibUtil;
import com.izhimu.seas.base.entity.SysAuthMenu;
import com.izhimu.seas.base.entity.SysMenu;
import com.izhimu.seas.base.entity.SysUserRole;
import com.izhimu.seas.base.mapper.SysMenuMapper;
import com.izhimu.seas.base.param.SysMenuParam;
import com.izhimu.seas.base.service.SysAuthMenuService;
import com.izhimu.seas.base.service.SysMenuService;
import com.izhimu.seas.base.service.SysUserRoleService;
import com.izhimu.seas.base.service.SysUserService;
import com.izhimu.seas.base.vo.SysMenuVO;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

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
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    private SysUserService userService;

    @Resource
    private SysUserRoleService userRoleService;

    @Resource
    private SysAuthMenuService authMenuService;

    @Override
    public List<Tree<Long>> tree(SysMenuParam param) {
        List<SysMenu> list = this.paramQuery().param(param).orderBy().wrapper().list();
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("key");
        config.setNameKey("label");
        return TreeUtil.build(list, 0L, config,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getMenuName());
                    tree.setWeight(treeNode.getSort() + "-" + treeNode.getId());
                });
    }

    @Override
    public List<SysMenuVO> auth() {
        User user = userService.getCurrentUser();
        if (Boolean.TRUE.equals(user.getIsSuper())) {
            return CglibUtil.copyList(this.lambdaQuery().orderByAsc(SysMenu::getSort).list(), SysMenuVO::new);
        }
        List<SysMenu> all = this.lambdaQuery().eq(SysMenu::getDisplay, 1).list();
        Map<Long, SysMenu> allMap = all.stream().collect(Collectors.toMap(SysMenu::getId, v -> v));
        Set<Long> roles = userRoleService.lambdaQuery()
                .select(SysUserRole::getRoleId)
                .eq(SysUserRole::getUserId, user.getId())
                .list()
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toSet());
        if (roles.isEmpty()) {
            return List.of();
        }
        Set<Long> menus = authMenuService.lambdaQuery()
                .select(SysAuthMenu::getMenuId)
                .in(SysAuthMenu::getRoleId, roles)
                .list()
                .stream()
                .map(SysAuthMenu::getMenuId)
                .collect(Collectors.toSet());
        if (menus.isEmpty()) {
            return List.of();
        }
        Set<SysMenu> result = new HashSet<>();
        menus.forEach(v -> {
            result.add(allMap.get(v));
            getAuthMenuIds(result, allMap, v);
        });
        return CglibUtil.copyList(result.stream().sorted(Comparator.comparing(SysMenu::getSort)).collect(Collectors.toList()), SysMenuVO::new);
    }

    /**
     * 获取权限菜单
     *
     * @param menuIds 权限菜单
     * @param all     所有菜单
     * @param id      权限ID
     */
    private void getAuthMenuIds(Set<SysMenu> menuIds, Map<Long, SysMenu> all, Long id) {
        if (all.containsKey(id)) {
            SysMenu sysMenu = all.get(id);
            menuIds.add(sysMenu);
            getAuthMenuIds(menuIds, all, sysMenu.getParentId());
        }
    }
}
