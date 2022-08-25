package com.izhimu.seas.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.mybatis.entity.Pagination;

import java.util.function.Supplier;

/**
 * 基础服务层接口封装
 *
 * @author haoran
 * @version v1.0
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 分页查询视图层
     *
     * @param page  分页参数
     * @param param 查询参数
     * @return 视图层分页
     */
    Pagination<T> page(Pagination<T> page, Object param);

    /**
     * 分页查询视图层
     *
     * @param page   分页参数
     * @param param  查询参数
     * @param target 目标对象
     * @return 视图层分页
     */
    <E> Pagination<E> page(Pagination<T> page, Object param, Supplier<E> target);

    /**
     * 根据ID获取对象
     *
     * @param id    Long
     * @param clazz Class
     * @return E
     */
    <E> E get(Long id, Class<E> clazz);
}
