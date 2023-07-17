package com.izhimu.seas.base.service;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

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
}
