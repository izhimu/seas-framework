package com.izhimu.seas.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 密码错误异常
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public class PwdErrorException extends AuthenticationException {

    public PwdErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    public PwdErrorException(String msg) {
        super(msg);
    }
}
