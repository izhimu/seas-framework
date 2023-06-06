package com.izhimu.seas.security.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出成功处理器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        Result<?> result;
        // 清除Session
        try {
            result = Result.ok();
        } catch (Exception e) {
            log.error("", e);
            result = Result.error();
        }
        httpServletResponse.setStatus(result.httpStatus().value());
        ServletUtil.write(httpServletResponse, result.toString(), MediaType.APPLICATION_JSON_VALUE);
        Login loginDTO = ((User) authentication.getPrincipal()).getLogin();
        loginDTO.setStatus(1);
        EventManager.trigger(CoreEvent.E_LOGIN, loginDTO);
    }
}
