package com.izhimu.seas.generate.service;

import com.izhimu.seas.generate.entity.GenInfo;

/**
 * 代码生成服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface GenInfoService {

    /**
     * 获取生成信息
     *
     * @param sourceId 数据源ID
     * @param table    表名
     * @return 生成信息
     */
    GenInfo getInfo(Long sourceId, String table);

    /**
     * 生成
     *
     * @param genInfo 生成信息
     */
    void create(GenInfo genInfo);

    /**
     * 预览生成内容
     *
     * @param genInfo 生成信息
     */
    void preview(GenInfo genInfo);
}
