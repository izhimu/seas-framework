package com.izhimu.seas.security.controller;

import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.security.service.LoginService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 安全控制层
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Resource
    private EncryptService<EncryptKey, String> encryptService;
    @Resource
    private LoginService loginService;

    /**
     * 获取加密秘钥
     *
     * @return EncryptKey
     */
    @OperationLog(value = "安全控制-获取加密秘钥", enable = false)
    @GetMapping("/encrypt/key")
    public EncryptKey getEncryptKey() {
        return encryptService.createEncryptKey(SecurityConstant.ENCRYPT_EXPIRE).toView();
    }

    @OperationLog(value = "安全控制-登录", enable = false)
    @PostMapping("/login")
    public Login login(@RequestBody Login dto) {
        return loginService.login(dto);
    }

    @OperationLog(value = "安全控制-退出", enable = false)
    @PostMapping("/logout")
    public void logout() {
        loginService.logout();
    }
}
