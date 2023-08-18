package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasAuthMenu;
import com.izhimu.seas.base.mapper.BasAuthMenuMapper;
import com.izhimu.seas.base.service.BasAuthMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单权限服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAuthMenuServiceImpl extends ServiceImpl<BasAuthMenuMapper, BasAuthMenu> implements BasAuthMenuService {
    @Override
    public boolean removeByRoleId(Serializable roleId) {
        return this.lambdaUpdate()
                .eq(BasAuthMenu::getRoleId, roleId)
                .remove();
    }

    @Override
    public List<Long> findMenuIdByRoleId(Long roleId) {
        return this.lambdaQuery()
                .select(BasAuthMenu::getMenuId)
                .eq(BasAuthMenu::getRoleId, roleId)
                .eq(BasAuthMenu::getIsChecked, 1)
                .list()
                .stream()
                .map(BasAuthMenu::getMenuId)
                .toList();
    }

    @Override
    public Set<Long> findMenuIdByRoleIdDistinct(Collection<Long> roleIds) {
        return this.lambdaQuery()
                .select(BasAuthMenu::getMenuId)
                .in(BasAuthMenu::getRoleId, roleIds)
                .list()
                .stream()
                .map(BasAuthMenu::getMenuId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> findRoleIdByMenuIdDistinct(Long menuId) {
        return this.lambdaQuery()
                .select(BasAuthMenu::getRoleId)
                .eq(BasAuthMenu::getMenuId, menuId)
                .list()
                .stream()
                .map(BasAuthMenu::getRoleId)
                .collect(Collectors.toSet());
    }
}
