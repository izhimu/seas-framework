package com.izhimu.seas.base.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasAuthOrg;
import com.izhimu.seas.base.entity.BasDict;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.base.entity.BasUserOrg;
import com.izhimu.seas.base.mapper.BasOrgMapper;
import com.izhimu.seas.base.service.BasAuthOrgService;
import com.izhimu.seas.base.service.BasDictService;
import com.izhimu.seas.base.service.BasOrgService;
import com.izhimu.seas.base.service.BasUserOrgService;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
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
    private BasUserOrgService userOrgService;

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
    public boolean removeById(Serializable id) {
        boolean b = super.removeById(id);
        userOrgService.lambdaUpdate()
                .eq(BasUserOrg::getOrgId, id)
                .remove();
        authOrgService.lambdaUpdate()
                .eq(BasAuthOrg::getOrgId, id)
                .remove();
        return b;
    }

    @Override
    public List<Tree<Long>> tree(BasOrg param) {
        List<BasOrg> list = this.paramQuery().param(param).wrapper().list();
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("key");
        config.setNameKey("label");
        return TreeUtil.build(list, 0L, config,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getOrgName());
                    tree.setWeight(treeNode.getSort());
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
