package com.izhimu.seas.base.service;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysDict;
import com.izhimu.seas.base.param.SysDictParam;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

/**
 * 字典服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysDictService extends IBaseService<SysDict> {

    /**
     * 获取树数据
     *
     * @param param 参数
     * @return 树数据
     */
    List<Tree<Long>> tree(SysDictParam param);

    /**
     * 根据字典编号获取选择器数据
     *
     * @param dictCode 字典编号
     * @return 选择器数据
     */
    List<Select<Long>> select(String dictCode);
}
