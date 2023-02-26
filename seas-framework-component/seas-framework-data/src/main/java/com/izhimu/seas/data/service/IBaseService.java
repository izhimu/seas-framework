package com.izhimu.seas.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.entity.Pagination;

import java.util.function.Supplier;

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
     * @param target 目标对象
     * @return 视图层分页
     */
    <V> Pagination<V> page(Pagination<T> page, Object param, Supplier<V> target);

    /**
     * 根据ID获取对象
     *
     * @param id    Long
     * @param clazz Class
     * @return E
     */
    <V> V get(Long id, Class<V> clazz);

    /**
     * 新增
     *
     * @param entity 实体
     * @return ID
     */
    Long add(T entity);
}
