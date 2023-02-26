package com.izhimu.seas.data.service.impl;

import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.entity.Pagination;
import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.data.wrapper.ParamWrapper;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * 基础服务层实现封装
 *
 * @author haoran
 * @version v1.0
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends IdEntity> extends ServiceImpl<M, T> implements IBaseService<T> {

    /**
     * 查询条件传入
     *
     * @return ParamWrapper<T>
     */
    protected ParamWrapper<T> paramQuery() {
        return ParamWrapper.query(this);
    }

    @Override
    public <E> Pagination<E> page(Pagination<T> page, Object param, Supplier<E> target) {
        Pagination<T> result = this.paramQuery().param(param).orderBy().wrapper().page(page);
        return Pagination.of(result, target);
    }

    @Override
    public <E> E get(Long id, Class<E> clazz) {
        T byId = super.getById(id);
        if (Objects.isNull(byId)) {
            return null;
        }
        return CglibUtil.copy(byId, clazz);
    }

    @Override
    public Long add(T entity) {
        boolean save = super.save(entity);
        if (save) {
            return entity.getId();
        }
        return null;
    }
}
