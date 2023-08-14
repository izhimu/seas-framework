package com.izhimu.seas.base.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.izhimu.seas.base.entity.*;
import com.izhimu.seas.base.mapper.BasRoleMapper;
import com.izhimu.seas.base.service.*;
import com.izhimu.seas.core.entity.DataPermission;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
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
    private BasAuthOrgService authOrgService;
    @Resource
    private BasUserRoleService userRoleService;
    @Resource
    private BasMenuService menuService;
    @Resource
    private BasOrgService orgService;

    @Override
    public void updateRoleMenu(BasAuthMenu entity) {
        authMenuService.removeByRoleId(entity.getRoleId());
        List<BasAuthMenu> authMenuList = entity.getMenuIds().stream()
                .map(v -> {
                    BasAuthMenu authMenu = new BasAuthMenu();
                    authMenu.setRoleId(entity.getRoleId());
                    authMenu.setMenuId(v);
                    authMenu.setIsChecked(1);
                    return authMenu;
                }).collect(Collectors.toList());
        authMenuService.saveBatch(authMenuList);
        List<BasAuthMenu> parentAuthMenuList = entity.getMenuPIds().stream()
                .map(v -> {
                    BasAuthMenu authMenu = new BasAuthMenu();
                    authMenu.setRoleId(entity.getRoleId());
                    authMenu.setMenuId(v);
                    authMenu.setIsChecked(0);
                    return authMenu;
                }).collect(Collectors.toList());
        authMenuService.saveBatch(parentAuthMenuList);
    }

    @Override
    public void updateRoleOrg(BasAuthOrg entity) {
        authOrgService.removeByRoleId(entity.getRoleId());
        List<BasAuthOrg> authOrgList = entity.getOrgIds().stream()
                .map(v -> {
                    BasAuthOrg authOrg = new BasAuthOrg();
                    authOrg.setRoleId(entity.getRoleId());
                    authOrg.setOrgId(v);
                    return authOrg;
                }).collect(Collectors.toList());
        authOrgService.saveBatch(authOrgList);
    }

    @Override
    public void updateUserRole(BasUserRole entity) {
        userRoleService.removeByRoleId(entity.getRoleId());
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
    public List<Select<Long>> select() {
        return this.list()
                .stream()
                .map(v -> new Select<>(v.getRoleName(), v.getId()))
                .toList();
    }

    @Override
    public List<String> findMenuAuthByUserId(User user) {
        Set<Long> roleIds = userRoleService.findRoleIdByUserIdDistinct(user.getId());
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptyList();
        }
        Set<Long> menuIds = authMenuService.findMenuIdByRoleIdDistinct(roleIds);
        if (CollUtil.isEmpty(menuIds)) {
            return Collections.emptyList();
        }
        return menuService.findMenuCodeByIds(menuIds);
    }

    @Override
    public DataPermission getDataPermissionByUserId(User user) {
        Set<Long> roleIds = userRoleService.findRoleIdByUserIdDistinct(user.getId());
        if (CollUtil.isEmpty(roleIds)) {
            return new DataPermission();
        }
        List<BasRole> roleList = this.lambdaQuery()
                .in(BasRole::getId, roleIds)
                .list();
        return getDataPermission(roleList, user);
    }

    @Override
    public List<String> findNameById(Collection<Long> id) {
        return this.lambdaQuery()
                .select(BasRole::getRoleName)
                .in(BasRole::getId, id)
                .list()
                .stream()
                .map(BasRole::getRoleName)
                .distinct()
                .toList();
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean b = super.removeById(id);
        userRoleService.removeByRoleId(id);
        authMenuService.removeByRoleId(id);
        authOrgService.removeByRoleId(id);
        return b;
    }

    /**
     * 获取默认权限载荷
     *
     * @param roleList 角色列表
     * @param user     用户
     * @return 权限载荷
     */
    private DataPermission getDataPermission(List<BasRole> roleList, User user) {
        DataPermission permission = new DataPermission();
        List<Long> ids = new ArrayList<>();
        if (Boolean.TRUE.equals(user.getIsSuper())) {
            permission.setType(0);
            permission.setAuthList(ids);
            return permission;
        }
        Set<Integer> typeSet = roleList.stream()
                .map(BasRole::getAuthType)
                .collect(Collectors.toSet());
        // 仅自己
        if (typeSet.contains(4)) {
            permission.setType(4);
            ids.add(user.getId());
            permission.setAuthList(ids);
            return permission;
        }
        // 全部数据
        if (typeSet.size() == 1 && typeSet.contains(0)) {
            permission.setType(0);
            permission.setAuthList(ids);
            return permission;
        } else {
            List<BasRole> orgAuth = roleList.stream()
                    .filter(v -> v.getAuthType() > 0 && v.getAuthType() < 4)
                    .toList();
            Set<Integer> subTypeSet = orgAuth.stream()
                    .map(BasRole::getAuthType)
                    .collect(Collectors.toSet());
            permission.setType(subTypeSet.size() == 1 ? subTypeSet.stream().findFirst().get() : 1);
            for (BasRole role : orgAuth) {
                // 自定义部门
                if (Objects.equals(1, role.getAuthType())) {
                    ids.addAll(authOrgService.findOrgIdByRoleIdDistinct(role.getId()));
                }
                // 本部门及下属部门
                else if (Objects.equals(2, role.getAuthType())) {
                    Long orgId = user.getOrgId();
                    if (Objects.nonNull(user.getOrgId())) {
                        BasOrg org = orgService.getById(orgId);
                        Set<Long> deptIds = orgService.findSubOrgId(org.getOrgCode());
                        if (!deptIds.isEmpty()) {
                            ids.addAll(deptIds);
                        }
                    }
                }
                // 本部门
                else if (Objects.equals(3, role.getAuthType())) {
                    Long orgId = user.getOrgId();
                    if (Objects.nonNull(user.getOrgId())) {
                        ids.add(orgId);
                    }
                }
            }
        }
        if (ids.isEmpty()) {
            ids.add(DataPermission.NO_PERMISSION);
        }
        permission.setAuthList(ids);
        return permission;
    }
}
