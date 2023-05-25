/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.izhimu.seas.security.controller;

import com.izhimu.seas.captcha.model.Captcha;
import com.izhimu.seas.captcha.service.CaptchaService;
import com.izhimu.seas.core.annotation.OperationLog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 验证码控制层
 */
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Resource
    private CaptchaService captchaService;

    @OperationLog(value = "验证码-获取", enable = false)
    @PostMapping("/get")
    public Captcha get(@RequestBody Captcha data) {
        return captchaService.get(data);
    }

    @OperationLog(value = "验证码-校验", enable = false)
    @PostMapping("/check")
    public Captcha check(@RequestBody Captcha data) {
        return captchaService.check(data);
    }
}
