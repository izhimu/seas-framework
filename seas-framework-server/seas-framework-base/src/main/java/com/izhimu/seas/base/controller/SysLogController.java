package com.izhimu.seas.base.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.SysLog;
import com.izhimu.seas.base.service.SysLogService;
import com.izhimu.seas.core.annotation.OperationLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 操作日志控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Resource
    private SysLogService service;

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Page Page}
     * @param param 查询参数 {@link SysLog SysLog}
     * @return 分页数据 {@link SysLog SysLog}
     */
    @OperationLog(value = "操作日志-分页查询", enable = false)
    @GetMapping("/page")
    public Page<SysLog> page(Page<SysLog> page, SysLog param) {
        return service.findPage(page, param);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link SysLog SysLog}
     */
    @OperationLog(value = "操作日志-详情", enable = false)
    @GetMapping("/{id}")
    public SysLog get(@PathVariable Long id) {
        return service.get(id);
    }
}
