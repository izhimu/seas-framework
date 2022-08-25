package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.constant.SecurityConstant;
import com.izhimu.seas.base.entity.EncryptKey;
import com.izhimu.seas.base.service.EncryptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 安全控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Resource
    private EncryptService<EncryptKey, String> encryptService;

    /**
     * 获取加密秘钥
     *
     * @return EncryptKey
     */
    @GetMapping("/encrypt/key")
    public EncryptKey getEncryptKey() {
        return encryptService.createEncryptKey(SecurityConstant.ENCRYPT_EXPIRE).toView();
    }
}
