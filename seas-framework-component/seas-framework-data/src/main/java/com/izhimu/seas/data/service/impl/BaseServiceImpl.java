package com.izhimu.seas.data.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.entity.Pagination;
import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.data.wrapper.ParamWrapper;

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
    public Pagination<T> page(Pagination<T> page, Object param) {
        return this.paramQuery().param(param).orderBy().wrapper().page(page);
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
