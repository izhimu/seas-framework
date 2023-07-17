package com.izhimu.seas.base.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.base.mapper.BasOrgMapper;
import com.izhimu.seas.base.service.BasOrgService;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 组织架构服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasOrgServiceImpl extends BaseServiceImpl<BasOrgMapper, BasOrg> implements BasOrgService {
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
}
