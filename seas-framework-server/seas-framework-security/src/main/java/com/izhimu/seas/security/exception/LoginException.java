package com.izhimu.seas.security.exception;

import com.izhimu.seas.core.web.ResultCode;
import org.springframework.security.core.AuthenticationException;

/**
 * 登录异常
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public class LoginException extends AuthenticationException {

    public final ResultCode resultCode;

    public LoginException(ResultCode resultCode, String msg, Throwable t) {
        super(msg, t);
        this.resultCode = resultCode;
    }

    public LoginException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }
}
