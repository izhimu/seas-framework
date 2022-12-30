package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.SysAccount;
import com.izhimu.seas.base.service.SysAccountService;
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

    @GetMapping("/list")
    public List<SysAccount> list() {
        return service.list();
    }

    @GetMapping("/like")
    public List<Select<String>> like(String search) {
        return service.likeAccount(search);
    }
}
