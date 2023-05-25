package com.izhimu.seas.base.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.izhimu.seas.base.entity.SysDict;
import com.izhimu.seas.base.mapper.SysDictMapper;
import com.izhimu.seas.base.service.SysDictService;
import com.izhimu.seas.core.web.entity.Select;
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
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    public List<Tree<Long>> tree(SysDict param) {
        List<SysDict> list = this.paramQuery().param(param).wrapper().list();
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
        Optional<SysDict> sysDict = this.lambdaQuery()
                .select(SysDict::getId)
                .eq(SysDict::getDictCode, dictCode)
                .oneOpt();
        return sysDict.map(dict -> this.lambdaQuery()
                .select(SysDict::getDictName, SysDict::getId)
                .eq(SysDict::getParentId, dict.getId())
                .orderByAsc(SysDict::getSort)
                .list()
                .stream()
                .map(item -> new Select<>(item.getDictName(), item.getId()))
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }
}
