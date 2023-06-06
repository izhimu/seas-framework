package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.base.service.BasAccountService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Select;
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
@RequestMapping("/bas/account")
public class BasAccountController {

    @Resource
    private BasAccountService service;

    /**
     * 列表
     *
     * @return 数据列表 {@link BasAccount SysAccount}
     */
    @OperationLog("用户账号-列表")
    @GetMapping("/list")
    public List<BasAccount> list() {
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
