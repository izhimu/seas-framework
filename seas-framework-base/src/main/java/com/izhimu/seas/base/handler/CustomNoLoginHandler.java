package com.izhimu.seas.base.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.web.ResultCode;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 未登录控制器
 *
 * @author haoran
 * @version v1.0
 */
public class CustomNoLoginHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        Result<?> result;
        if (e instanceof UsernameNotFoundException) {
            result = Result.fail(ResultCode.LOGIN_PASSWORD_ERROR);
        } else if (e instanceof InsufficientAuthenticationException) {
            result = Result.fail(ResultCode.LOGIN_OVERDUE);
        } else {
            result = Result.fail(ResultCode.LOGIN_ERROR);
        }
        httpServletResponse.setStatus(result.httpStatus().value());
        ServletUtil.write(httpServletResponse, result.toString(), MediaType.APPLICATION_JSON_VALUE);
    }
}
