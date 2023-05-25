package com.izhimu.seas.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.entity.Pagination;

/**
 * 基础服务层接口封装
 *
 * @author haoran
 * @version v1.0
 */
public interface IBaseService<T extends IdEntity> extends IService<T> {

    /**
     * 分页查询视图层
     *
     * @param page   分页参数
     * @param param  查询参数
     * @return 视图层分页
     */
    Pagination<T> page(Pagination<T> page, Object param);

    /**
     * 新增
     *
     * @param entity 实体
     * @return ID
     */
    Long add(T entity);
}
