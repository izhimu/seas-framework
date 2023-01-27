package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.SysAccount;
import com.izhimu.seas.base.service.SysAccountService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.web.entity.Select;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户账号控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/account")
public class SysAccountController {

    @Resource
    private SysAccountService service;

    /**
     * 列表
     *
     * @return 数据列表 {@link SysAccount SysAccount}
     */
    @OperationLog("用户账号-列表")
    @GetMapping("/list")
    public List<SysAccount> list() {
        return service.list();
    }

    /**
     * 模糊查询
     *
     * @param search 查询信息
     * @return 数据列表 {@link Select Select}
     */
    @OperationLog("用户账号-模糊查询")
    @GetMapping("/like")
    public List<Select<String>> like(String search) {
        return service.likeAccount(search);
    }
}
