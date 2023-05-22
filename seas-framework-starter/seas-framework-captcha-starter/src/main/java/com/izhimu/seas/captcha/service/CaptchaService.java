package com.izhimu.seas.captcha.service;

import com.izhimu.seas.captcha.model.Captcha;

/**
 * 验证码服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface CaptchaService {

    /**
     * 获取验证码
     *
     * @param captcha Captcha
     * @return Captcha
     */
    Captcha get(Captcha captcha);

    /**
     * 核对验证码(前端)
     *
     * @param captcha Captcha
     * @return Captcha
     */
    Captcha check(Captcha captcha);

    /**
     * 二次校验验证码(后端)
     *
     * @param captcha Captcha
     * @return boolean
     */
    boolean verification(Captcha captcha);
}
