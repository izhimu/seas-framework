package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.base.mapper.BasUserRoleMapper;
import com.izhimu.seas.base.service.BasUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色中间服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasUserRoleServiceImpl extends ServiceImpl<BasUserRoleMapper, BasUserRole> implements BasUserRoleService {

    @Override
    public boolean removeByRoleId(Serializable roleId) {
        return this.lambdaUpdate()
                .eq(BasUserRole::getRoleId, roleId)
                .remove();
    }

    @Override
    public boolean removeByUserId(Serializable userId) {
        return this.lambdaUpdate()
                .eq(BasUserRole::getUserId, userId)
                .remove();
    }

    @Override
    public List<Long> findUserIdByRoleId(Long roleId) {
        return this.lambdaQuery()
                .select(BasUserRole::getUserId)
                .eq(BasUserRole::getRoleId, roleId)
                .list()
                .stream()
                .map(BasUserRole::getUserId)
                .toList();
    }

    @Override
    public Set<Long> findUserIdByRoleIdDistinct(Long roleId) {
        return this.lambdaQuery()
                .select(BasUserRole::getUserId)
                .eq(BasUserRole::getRoleId, roleId)
                .list()
                .stream()
                .map(BasUserRole::getUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Long> findRoleIdByUserId(Long userId) {
        return this.lambdaQuery()
                .select(BasUserRole::getRoleId)
                .eq(BasUserRole::getUserId, userId)
                .list()
                .stream()
                .map(BasUserRole::getRoleId)
                .toList();
    }

    @Override
    public Set<Long> findRoleIdByUserIdDistinct(Long userId) {
        return this.lambdaQuery()
                .select(BasUserRole::getRoleId)
                .eq(BasUserRole::getUserId, userId)
                .list()
                .stream()
                .map(BasUserRole::getRoleId)
                .collect(Collectors.toSet());
    }
}
