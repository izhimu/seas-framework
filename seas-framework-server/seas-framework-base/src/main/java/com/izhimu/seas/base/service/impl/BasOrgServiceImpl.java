package com.izhimu.seas.base.service.impl;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasAuthOrg;
import com.izhimu.seas.base.entity.BasDict;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.base.mapper.BasOrgMapper;
import com.izhimu.seas.base.service.BasAuthOrgService;
import com.izhimu.seas.base.service.BasDictService;
import com.izhimu.seas.base.service.BasOrgService;
import com.izhimu.seas.core.entity.DataPermission;
import com.izhimu.seas.core.entity.RefreshSession;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.utils.CodeUtil;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.security.util.SecurityUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 组织架构服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasOrgServiceImpl extends BaseServiceImpl<BasOrgMapper, BasOrg> implements BasOrgService {

    private static final String DICT_KEY = "org.type";

    @Resource
    private BasDictService dictService;
    @Resource
    private BasAuthOrgService authOrgService;

    @Override
    public Page<BasOrg> page(Page<BasOrg> page, Object param) {
        Page<BasOrg> result = super.page(page, param);
        for (BasOrg record : result.getRecords()) {
            String orgType = record.getOrgType();
            if (StrUtil.isBlank(orgType)) {
                continue;
            }
            BasDict dict = dictService.getDictByCode(DICT_KEY, orgType);
            record.setOrgTypeName(dict.getDictName());
        }
        return result;
    }

    @Override
    public Long add(BasOrg entity) {
        DataPermission permission = SecurityUtil.getPermission();
        if (Objects.equals(1, permission.simpleType()) &&
                !permission.getAuthList().contains(entity.getParentId()) ||
                Objects.equals(2, permission.simpleType())) {
            throw new ValidateException("无权限在此部门下添加子部门！");
        }
        List<BasOrg> list = this.lambdaQuery()
                .select(BasOrg::getOrgCode)
                .eq(BasOrg::getParentId, entity.getParentId())
                .orderByDesc(BasOrg::getOrgCode)
                .list();
        if (list.isEmpty()) {
            BasOrg org = this.getById(entity.getParentId());
            entity.setOrgCode(org.getOrgCode().concat("001"));
        } else {
            BasOrg last = list.get(0);
            String code = CodeUtil.generateTreeCode(last.getOrgCode());
            entity.setOrgCode(code);
        }
        return super.add(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        boolean b = super.removeById(id);
        authOrgService.lambdaUpdate()
                .eq(BasAuthOrg::getOrgId, id)
                .remove();
        EventManager.trigger(CoreEvent.E_SESSION_REFRESH, RefreshSession.org(id));
        return b;
    }

    @Override
    public List<Tree<Long>> tree(BasOrg param) {
        List<BasOrg> all = this.paramQuery().param(param).wrapper()
                .select("id", "parent_id", "org_name", "sort").list();
        List<Long> list = this.paramQuery().param(param).permissions().wrapper()
                .select("id").list()
                .stream().map(BasOrg::getId).toList();
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("key");
        config.setNameKey("label");
        return TreeUtil.build(all, 0L, config, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setName(treeNode.getOrgName());
            tree.setWeight(treeNode.getSort());
            tree.putExtra("disabled", !list.contains(tree.getId()));
        });
    }

    @Override
    public Set<Long> findSubOrgId(String code) {
        return this.lambdaQuery()
                .select(BasOrg::getId)
                .likeRight(BasOrg::getOrgCode, code)
                .list()
                .stream()
                .map(BasOrg::getId)
                .collect(Collectors.toSet());
    }
}
