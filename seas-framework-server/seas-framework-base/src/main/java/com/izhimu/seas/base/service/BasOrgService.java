package com.izhimu.seas.base.service;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;
import java.util.Set;

/**
 * 组织架构服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasOrgService extends IBaseService<BasOrg> {

    /**
     * 获取树数据
     *
     * @param param 参数
     * @return 树数据
     */
    List<Tree<Long>> tree(BasOrg param);

    /**
     * 根据组织编号查询本部门及下属部门ID集合
     *
     * @param code 组织编号
     * @return 本部门及下属部门ID集合
     */
    Set<Long> findSubOrgId(String code);
}
