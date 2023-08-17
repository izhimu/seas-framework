package com.izhimu.seas.security.exception;

import com.izhimu.seas.core.web.ResultCode;
import lombok.Getter;

/**
 * 登录异常
 *
 * @author haoran
 * @version v1.0
 */
@Getter
@SuppressWarnings("unused")
public class SecurityException extends RuntimeException {

    private final ResultCode resultCode;

    public SecurityException(ResultCode resultCode, String msg, Throwable t) {
        super(msg, t);
        this.resultCode = resultCode;
    }

    public SecurityException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

    public SecurityException(ResultCode resultCode) {
        super(resultCode.getTips());
        this.resultCode = resultCode;
    }
}
