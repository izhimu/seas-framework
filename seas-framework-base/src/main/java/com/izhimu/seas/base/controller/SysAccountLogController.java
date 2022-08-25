package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.SysAccountLog;
import com.izhimu.seas.base.param.SysAccountLogParam;
import com.izhimu.seas.base.service.SysAccountLogService;
import com.izhimu.seas.base.vo.SysAccountLogVO;
import com.izhimu.seas.mybatis.entity.Pagination;
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

    @GetMapping("/page")
    public Pagination<SysAccountLogVO> page(Pagination<SysAccountLog> page, SysAccountLogParam param) {
        return service.page(page, param, SysAccountLogVO::new);
    }

    @GetMapping("/{id}")
    public SysAccountLogVO get(@PathVariable Long id) {
        return service.get(id);
    }
}
