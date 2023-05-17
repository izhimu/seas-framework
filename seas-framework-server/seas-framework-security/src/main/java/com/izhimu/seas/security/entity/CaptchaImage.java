package com.izhimu.seas.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 验证码图片
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class CaptchaImage implements Serializable {

    private String image;
    private String originalImage;
    private Boolean result;
    private String secretKey;
    private String token;
}
