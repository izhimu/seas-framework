package com.izhimu.seas.base.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.web.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 无权限处理器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        log.error("", e);
        Result<?> fail = Result.fail(ResultCode.NO_PERMISSION);
        httpServletResponse.setStatus(fail.httpStatus().value());
        ServletUtil.write(httpServletResponse, fail.toString(), MediaType.APPLICATION_JSON_VALUE);
    }
}
