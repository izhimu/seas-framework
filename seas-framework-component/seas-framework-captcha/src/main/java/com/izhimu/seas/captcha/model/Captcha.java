package com.izhimu.seas.captcha.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 验证码视图实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class Captcha implements Serializable {


    /**
     * 滑块图片
     */
    private String blockImage;
    /**
     * 原生图片
     */
    private String originalImage;
    /**
     * 加密密钥
     */
    private String secretKey;
    /**
     * 点坐标
     */
    private String pointJson;
    /**
     * token
     */
    private String token;
    /**
     * 校验结果
     */
    private Boolean result = false;
    /**
     * 二次校验参数
     */
    private String captchaVerification;
    /**
     * 滑块点选坐标
     */
    private Point point;

    /**
     * 转换VO对象
     *
     * @return Captcha
     */
    public Captcha toView() {
        this.point = null;
        return this;
    }
}
