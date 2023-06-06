package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.base.entity.BasAuthMenu;
import com.izhimu.seas.base.entity.BasRole;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.base.mapper.BasRoleMapper;
import com.izhimu.seas.base.service.BasAuthMenuService;
import com.izhimu.seas.base.service.BasRoleService;
import com.izhimu.seas.base.service.BasUserRoleService;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasRoleServiceImpl extends BaseServiceImpl<BasRoleMapper, BasRole> implements BasRoleService {

    @Resource
    private BasAuthMenuService authMenuService;

    @Resource
    private BasUserRoleService userRoleService;

    @Override
    public void updateRoleMenu(BasAuthMenu menu) {
        authMenuService.lambdaUpdate()
                .eq(BasAuthMenu::getRoleId, menu.getRoleId())
                .remove();
        List<BasAuthMenu> authMenuList = menu.getMenuIds().stream()
                .map(v -> {
                    BasAuthMenu authMenu = new BasAuthMenu();
                    authMenu.setRoleId(menu.getRoleId());
                    authMenu.setMenuId(v);
                    return authMenu;
                }).collect(Collectors.toList());
        authMenuService.saveBatch(authMenuList);
    }

    @Override
    public List<String> getRoleMenu(Long roleId) {
        return authMenuService.lambdaQuery()
                .select(BasAuthMenu::getMenuId)
                .eq(BasAuthMenu::getRoleId, roleId)
                .list()
                .stream()
                .map(v -> String.valueOf(v.getMenuId()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserRole(BasUserRole entity) {
        userRoleService.lambdaUpdate()
                .eq(BasUserRole::getRoleId, entity.getRoleId())
                .remove();
        List<BasUserRole> userRoleList = entity.getUserIds().stream()
                .map(v -> {
                    BasUserRole userRole = new BasUserRole();
                    userRole.setRoleId(entity.getRoleId());
                    userRole.setUserId(v);
                    return userRole;
                }).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<String> getUserRole(Long roleId) {
        return userRoleService.lambdaQuery()
                .select(BasUserRole::getUserId)
                .eq(BasUserRole::getRoleId, roleId)
                .list()
                .stream()
                .map(v -> String.valueOf(v.getUserId()))
                .collect(Collectors.toList());
    }
}
