package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasAuthOrg;
import com.izhimu.seas.base.mapper.BasAuthOrgMapper;
import com.izhimu.seas.base.service.BasAuthOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 组织权限服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAuthOrgServiceImpl extends ServiceImpl<BasAuthOrgMapper, BasAuthOrg> implements BasAuthOrgService {
    @Override
    public boolean removeByRoleId(Serializable roleId) {
        return this.lambdaUpdate()
                .eq(BasAuthOrg::getRoleId, roleId)
                .remove();
    }

    @Override
    public List<Long> findOrgIdByRoleId(Long roleId) {
        return this.lambdaQuery()
                .select(BasAuthOrg::getOrgId)
                .eq(BasAuthOrg::getRoleId, roleId)
                .list()
                .stream()
                .map(BasAuthOrg::getOrgId)
                .toList();
    }

    @Override
    public Set<Long> findOrgIdByRoleIdDistinct(Long roleId) {
        return this.lambdaQuery()
                .select(BasAuthOrg::getOrgId)
                .eq(BasAuthOrg::getRoleId, roleId)
                .list()
                .stream()
                .map(BasAuthOrg::getOrgId)
                .collect(Collectors.toSet());
    }
}
