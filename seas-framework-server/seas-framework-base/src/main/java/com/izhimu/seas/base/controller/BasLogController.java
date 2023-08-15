package com.izhimu.seas.base.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasLog;
import com.izhimu.seas.base.service.BasLogService;
import com.izhimu.seas.core.annotation.OperationLog;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/bas/log")
public class BasLogController {

    @Resource
    private BasLogService service;

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Page Page}
     * @param param 查询参数 {@link BasLog SysLog}
     * @return 分页数据 {@link BasLog SysLog}
     */
    @OperationLog(value = "操作日志-分页查询", enable = false)
    @GetMapping("/page")
    public Page<BasLog> page(Page<BasLog> page, BasLog param) {
        return service.findPage(page, param);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link BasLog SysLog}
     */
    @OperationLog(value = "操作日志-详情", enable = false)
    @GetMapping("/{id}")
    public BasLog get(@PathVariable Long id) {
        return service.get(id);
    }
}
