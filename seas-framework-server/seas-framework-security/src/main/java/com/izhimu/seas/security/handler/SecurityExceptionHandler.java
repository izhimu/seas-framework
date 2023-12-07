package com.izhimu.seas.security.handler;

import cn.dev33.satoken.exception.NotLoginException;
import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.web.ResultCode;
import com.izhimu.seas.security.exception.SecurityException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * 安全异常处理
 *
 * @author haoran
 * @version v1.0
 */
@Order(-105)
@ControllerAdvice
@ConditionalOnClass({ControllerAdvice.class})
public class SecurityExceptionHandler {

    /**
     * 未登录
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public ResponseEntity<Result<Serializable>> notLoginExceptionHandler() {
        return Result.fail(ResultCode.LOGIN_OVERDUE).buildResponseEntity();
    }

    /**
     * 安全异常
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = SecurityException.class)
    @ResponseBody
    public ResponseEntity<Result<Serializable>> securityExceptionHandler(SecurityException e) {
        return Result.fail(e.getResultCode(), e.getMessage()).buildResponseEntity();
    }
}
