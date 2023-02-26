package com.izhimu.seas.data.controller;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.entity.Pagination;
import com.izhimu.seas.data.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 基础控制层
 *
 * @author haoran
 * @version 1.0
 */
public abstract class AbsBaseController<S extends IBaseService<T>, T extends IdEntity, V, P> {

    /**
     * 服务层
     */
    protected S service;

    public S getService() {
        return service;
    }

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public void setService(S service) {
        this.service = service;
    }

    /**
     * 视图类类型
     */
    protected Class<V> voClass = getVoClass();

    /**
     * 视图提供者
     */
    protected Supplier<V> voTarget = getVoTarget();

    @SuppressWarnings("unchecked")
    public Class<V> getVoClass() {
        if (Objects.nonNull(this.voClass)) {
            return this.voClass;
        }
        return (Class<V>) ClassUtil.getTypeArgument(this.getClass(), 2);
    }

    public Supplier<V> getVoTarget() {
        if (Objects.nonNull(this.voTarget)) {
            return this.voTarget;
        }
        return () -> ReflectUtil.newInstance(getVoClass());
    }

    /**
     * 日志前缀
     *
     * @return String
     */
    @SuppressWarnings("unused")
    public abstract String logPrefix();

    @OperationLog("-分页查询")
    @GetMapping("/page")
    public Pagination<V> page(Pagination<T> page, P param) {
        return service.page(page, param, this.voTarget);
    }

    @OperationLog("-详情")
    @GetMapping("/{id}")
    public V get(@PathVariable Long id) {
        return service.get(id, this.voClass);
    }

    @OperationLog("-新增")
    @PostMapping
    public Long add(@RequestBody T entity) {
        return service.add(entity);
    }

    @OperationLog("-修改")
    @PutMapping
    public void edit(@RequestBody T entity) {
        service.updateById(entity);
    }

    @OperationLog("-删除")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Long id) {
        service.removeById(id);
    }

    @OperationLog("-批量删除")
    @DeleteMapping("/batch")
    public void batchDel(@RequestBody String data) {
        Map<?, ?> map = JsonUtil.toObject(data, Map.class);
        if (Objects.isNull(map)) {
            throw new ValidateException("参数缺失");
        }
        service.removeBatchByIds((Collection<?>) map.get("ids"));
    }
}
