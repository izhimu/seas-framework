package com.izhimu.seas.base.controller;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.base.service.BasAccountService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.security.util.SecurityUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    /**
     * 校验账号是否可用
     *
     * @param account BasAccount
     * @return true可用
     */
    @OperationLog("用户账号-校验可用")
    @PostMapping("/check")
    public boolean checkAccount(@RequestBody BasAccount account) {
        return service.checkAccount(account);
    }

    /**
     * 修改密码
     *
     * @param account 账号信息
     * @return 是否成功
     */
    @OperationLog("用户账号-修改密码")
    @PostMapping("/change/password")
    public boolean changePassword(@RequestBody BasAccount account) {
        if (StrUtil.isBlankIfStr(account.getUserAccount()) ||
                StrUtil.isBlankIfStr(account.getUserCertificate()) ||
                StrUtil.isBlankIfStr(account.getPasswordKey())) {
            throw new ValidateException("参数缺失");
        }
        User user = SecurityUtil.getUser();
        if (Objects.isNull(user) || !Objects.equals(account.getUserAccount(), user.getUserAccount())) {
            throw new ValidateException("账号不匹配");
        }
        return service.changePassword(account);
    }
}
