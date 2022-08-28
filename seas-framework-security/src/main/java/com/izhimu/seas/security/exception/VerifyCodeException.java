package com.izhimu.seas.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常
 *
 * @author haoran
 * @version v1.0
 */
public class VerifyCodeException extends AuthenticationException {

    public VerifyCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public VerifyCodeException(String msg) {
        super(msg);
    }
}
