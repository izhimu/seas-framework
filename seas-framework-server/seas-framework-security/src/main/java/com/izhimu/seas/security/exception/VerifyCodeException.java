package com.izhimu.seas.security.exception;

/**
 * 验证码异常
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public class VerifyCodeException extends RuntimeException {

    public VerifyCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public VerifyCodeException(String msg) {
        super(msg);
    }
}
