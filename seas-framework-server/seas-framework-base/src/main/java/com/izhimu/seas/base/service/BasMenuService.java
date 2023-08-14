package com.izhimu.seas.base.service;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.BasMenu;
import com.izhimu.seas.data.service.IBaseService;

import java.util.Collection;
import java.util.List;

/**
 * 菜单服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasMenuService extends IBaseService<BasMenu> {

    /**
     * 获取树数据
     *
     * @param param 参数
     * @return 树数据
     */
    List<Tree<Long>> tree(BasMenu param);

    /**
     * 返回有权限的菜单列表
     *
     * @return 权限菜单列表
     */
    List<BasMenu> auth();

    /**
     * 根据ID集合查询菜单编码集合
     *
     * @param ids ID集合
     * @return 菜单编码集合
     */
    List<String> findMenuCodeByIds(Collection<Long> ids);
}
