package com.izhimu.seas.base.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.izhimu.seas.base.entity.BasDict;
import com.izhimu.seas.base.mapper.BasDictMapper;
import com.izhimu.seas.base.service.BasDictService;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasDictServiceImpl extends BaseServiceImpl<BasDictMapper, BasDict> implements BasDictService {

    @Override
    public List<Tree<Long>> tree(BasDict param) {
        List<BasDict> list = this.paramQuery().param(param).wrapper().list();
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("key");
        config.setNameKey("label");
        return TreeUtil.build(list, 0L, config,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getDictName());
                    tree.setWeight(treeNode.getSort());
                });
    }

    @Override
    public List<Select<Long>> select(String dictCode) {
        Optional<BasDict> sysDict = this.lambdaQuery()
                .select(BasDict::getId)
                .eq(BasDict::getDictCode, dictCode)
                .oneOpt();
        return sysDict.map(dict -> this.lambdaQuery()
                .select(BasDict::getDictName, BasDict::getId)
                .eq(BasDict::getParentId, dict.getId())
                .orderByAsc(BasDict::getSort)
                .list()
                .stream()
                .map(item -> new Select<>(item.getDictName(), item.getId()))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    @Override
    public BasDict getDictByCode(String dictCode) {
        return this.lambdaQuery()
                .eq(BasDict::getDictCode, dictCode)
                .one();
    }
}
