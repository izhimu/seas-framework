package com.izhimu.seas.base.service;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.BasDict;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

/**
 * 字典服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasDictService extends IBaseService<BasDict> {

    /**
     * 获取树数据
     *
     * @param param 参数
     * @return 树数据
     */
    List<Tree<Long>> tree(BasDict param);

    /**
     * 根据字典编号获取选择器数据
     *
     * @param dictCode 字典编号
     * @return 选择器数据
     */
    List<Select<String>> select(String dictCode);

    /**
     * 根据字典编号获取字典
     *
     * @param dictType 字典类型
     * @param dictCode 字典编号
     * @return 字典
     */
    BasDict getDictByCode(String dictType, String dictCode);
}
