package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.base.entity.SysAuthMenu;
import com.izhimu.seas.base.entity.SysRole;
import com.izhimu.seas.base.entity.SysUserRole;
import com.izhimu.seas.base.mapper.SysRoleMapper;
import com.izhimu.seas.base.service.SysAuthMenuService;
import com.izhimu.seas.base.service.SysRoleService;
import com.izhimu.seas.base.service.SysUserRoleService;
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
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysAuthMenuService authMenuService;

    @Resource
    private SysUserRoleService userRoleService;

    @Override
    public void updateRoleMenu(SysAuthMenu menu) {
        authMenuService.lambdaUpdate()
                .eq(SysAuthMenu::getRoleId, menu.getRoleId())
                .remove();
        List<SysAuthMenu> authMenuList = menu.getMenuIds().stream()
                .map(v -> {
                    SysAuthMenu authMenu = new SysAuthMenu();
                    authMenu.setRoleId(menu.getRoleId());
                    authMenu.setMenuId(v);
                    return authMenu;
                }).collect(Collectors.toList());
        authMenuService.saveBatch(authMenuList);
    }

    @Override
    public List<String> getRoleMenu(Long roleId) {
        return authMenuService.lambdaQuery()
                .select(SysAuthMenu::getMenuId)
                .eq(SysAuthMenu::getRoleId, roleId)
                .list()
                .stream()
                .map(v -> String.valueOf(v.getMenuId()))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserRole(SysUserRole entity) {
        userRoleService.lambdaUpdate()
                .eq(SysUserRole::getRoleId, entity.getRoleId())
                .remove();
        List<SysUserRole> userRoleList = entity.getUserIds().stream()
                .map(v -> {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setRoleId(entity.getRoleId());
                    userRole.setUserId(v);
                    return userRole;
                }).collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<String> getUserRole(Long roleId) {
        return userRoleService.lambdaQuery()
                .select(SysUserRole::getUserId)
                .eq(SysUserRole::getRoleId, roleId)
                .list()
                .stream()
                .map(v -> String.valueOf(v.getUserId()))
                .collect(Collectors.toList());
    }
}
