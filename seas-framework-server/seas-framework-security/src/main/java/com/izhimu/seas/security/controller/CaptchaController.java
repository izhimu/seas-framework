/*
 *Copyright © 2018 anji-plus
 *安吉加加信息技术有限公司
 *http://www.anji-plus.com
 *All rights reserved.
 */
package com.izhimu.seas.security.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.security.entity.CaptchaImage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public Result<CaptchaImage> get(@RequestBody CaptchaVO data, HttpServletRequest request) {
        assert request.getRemoteHost() != null;
        data.setBrowserInfo(getRemoteId(request));
        ResponseModel responseModel = captchaService.get(data);
        return responseModelToResult(responseModel);
    }

    @OperationLog(value = "验证码-校验", enable = false)
    @PostMapping("/check")
    public Result<CaptchaImage> check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        data.setBrowserInfo(getRemoteId(request));
        ResponseModel responseModel = captchaService.check(data);
        return responseModelToResult(responseModel);
    }

    private Result<CaptchaImage> responseModelToResult(ResponseModel responseModel) {
        Object repData = responseModel.getRepData();
        if (repData instanceof CaptchaVO data) {
            CaptchaImage image = new CaptchaImage();
            image.setImage(data.getJigsawImageBase64());
            image.setOriginalImage(data.getOriginalImageBase64());
            image.setResult(data.getResult());
            image.setSecretKey(data.getSecretKey());
            image.setToken(data.getToken());
            return Result.ok(responseModel.getRepMsg(), image);
        }
        return Result.ok(responseModel.getRepMsg(), null);
    }

    public static String getRemoteId(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXff(xff);
        String ua = request.getHeader("user-agent");
        if (StringUtils.isNotBlank(ip)) {
            return ip + ua;
        }
        return request.getRemoteAddr() + ua;
    }

    private static String getRemoteIpFromXff(String xff) {
        if (StringUtils.isNotBlank(xff)) {
            String[] ipList = xff.split(",");
            return StringUtils.trim(ipList[0]);
        }
        return null;
    }
}
