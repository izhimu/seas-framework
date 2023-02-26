package com.izhimu.seas.base.service;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysMenu;
import com.izhimu.seas.base.param.SysMenuParam;
import com.izhimu.seas.base.vo.SysMenuVO;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

/**
 * 菜单服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysMenuService extends IBaseService<SysMenu> {

    /**
     * 获取树数据
     *
     * @param param 参数
     * @return 树数据
     */
    List<Tree<Long>> tree(SysMenuParam param);

    /**
     * 返回有权限的菜单列表
     *
     * @return 权限菜单列表
     */
    List<SysMenuVO> auth();
}
