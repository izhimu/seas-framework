package com.izhimu.seas.base.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasAccountLog;
import com.izhimu.seas.base.service.BasAccountLogService;
import com.izhimu.seas.core.annotation.OperationLog;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/bas/account/log")
public class BasAccountLogController {

    @Resource
    private BasAccountLogService service;

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Page Page}
     * @param param 查询参数 {@link BasAccountLog SysAccountLog}
     * @return 分页数据 {@link BasAccountLog SysAccountLog}
     */
    @OperationLog("登录日志-分页查询")
    @GetMapping("/page")
    public Page<BasAccountLog> page(Page<BasAccountLog> page, BasAccountLog param) {
        return service.findPage(page, param);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link BasAccountLog SysAccountLog}
     */
    @OperationLog("登录日志-详情")
    @GetMapping("/{id}")
    public BasAccountLog get(@PathVariable Long id) {
        return service.get(id);
    }
}
