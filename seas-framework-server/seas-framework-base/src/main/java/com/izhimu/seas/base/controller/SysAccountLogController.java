package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.SysAccountLog;
import com.izhimu.seas.base.param.SysAccountLogParam;
import com.izhimu.seas.base.service.SysAccountLogService;
import com.izhimu.seas.base.vo.SysAccountLogVO;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.entity.Pagination;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录日志控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/account/log")
public class SysAccountLogController {

    @Resource
    private SysAccountLogService service;

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Pagination Pagination}
     * @param param 查询参数 {@link SysAccountLogParam SysAccountLogParam}
     * @return 分页数据 {@link SysAccountLogVO SysAccountLogVO}
     */
    @OperationLog("登录日志-分页查询")
    @GetMapping("/page")
    public Pagination<SysAccountLogVO> page(Pagination<SysAccountLog> page, SysAccountLogParam param) {
        return service.findPage(page, param);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link SysAccountLogVO SysAccountLogVO}
     */
    @OperationLog("登录日志-详情")
    @GetMapping("/{id}")
    public SysAccountLogVO get(@PathVariable Long id) {
        return service.get(id);
    }
}
