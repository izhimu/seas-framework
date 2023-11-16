package com.izhimu.seas.data.controller;

import cn.hutool.core.exceptions.ValidateException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.service.IBaseService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 基础控制层
 *
 * @author haoran
 * @version 1.0
 */
@Getter
public abstract class AbsBaseController<S extends IBaseService<T>, T extends IdEntity> {

    /**
     * 服务层
     */
    protected S service;

    @Autowired
    public void setService(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") S service) {
        this.service = service;
    }

    /**
     * 日志前缀
     *
     * @return String
     */
    @SuppressWarnings("unused")
    public abstract String logPrefix();

    @OperationLog("@-分页查询")
    @GetMapping("/page")
    public Page<T> page(Page<T> page, T param) {
        return service.page(page, param);
    }

    @OperationLog("@-详情")
    @GetMapping("/{id}")
    public T get(@PathVariable Long id) {
        return service.getById(id);
    }

    @OperationLog("@-新增")
    @PostMapping
    public Long add(@RequestBody T entity) {
        return service.add(entity);
    }

    @OperationLog("@-修改")
    @PutMapping
    public void edit(@RequestBody T entity) {
        service.updateById(entity);
    }

    @OperationLog("@-删除")
    @DeleteMapping("/{id}")
    public void del(@PathVariable Long id) {
        service.removeById(id);
    }

    @OperationLog("@-批量删除")
    @DeleteMapping("/batch")
    public void batchDel(@RequestBody String data) {
        Map<?, ?> map = JsonUtil.toObject(data, Map.class);
        if (Objects.isNull(map)) {
            throw new ValidateException("参数缺失");
        }
        service.removeBatchByIds((Collection<?>) map.get("ids"));
    }
}
