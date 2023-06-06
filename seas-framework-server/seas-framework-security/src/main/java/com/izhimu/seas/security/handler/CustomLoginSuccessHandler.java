package com.izhimu.seas.security.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.cache.helper.RedisHelper;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.security.holder.LoginHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LoginHolder loginHolder;

    public CustomLoginSuccessHandler(LoginHolder loginHolder) {
        this.loginHolder = loginHolder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        Login loginDTO = loginHolder.get(true);
        try {
            createToken(httpServletRequest, httpServletResponse, authentication);
            // 清除错误次数
            String key = SecurityConstant.LOGIN_ERR_NUM_KEY.concat(":").concat(loginDTO.getAccount());
            RedisHelper.getInstance().del(key);
        } catch (Exception e) {
            log.error("", e);
            Result<?> result = Result.error();
            httpServletResponse.setStatus(result.httpStatus().value());
            ServletUtil.write(httpServletResponse, result.toString(), MediaType.APPLICATION_JSON_VALUE);
        }
        loginDTO.setStatus(0);
        EventManager.trigger(CoreEvent.E_LOGIN, loginDTO);
    }

    /**
     * 生成Token
     *
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     * @param authentication Authentication
     */
    private void createToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        Login login = new Login();
        login.setToken(request.getSession().getId());
        login.setTokenTimeout((long) request.getSession().getMaxInactiveInterval());
        login.setUserId(userDetails.getId());
        login.setPwdExpired(false);
        login.setUserName(userDetails.getNickName());
        Result<Object> result = Result.ok(login);
        ServletUtil.write(response, result.toString(), MediaType.APPLICATION_JSON_VALUE);
    }
}
