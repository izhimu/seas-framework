package com.izhimu.seas.security.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.cache.helper.RedisHelper;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.web.ResultCode;
import com.izhimu.seas.security.config.SecurityConfig;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.security.exception.LoginException;
import com.izhimu.seas.security.exception.PwdErrorException;
import com.izhimu.seas.security.exception.VerifyCodeException;
import com.izhimu.seas.security.holder.LoginHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * 登录失败处理器
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final LoginHolder loginHolder;
    private final SecurityConfig securityConfig;

    public CustomLoginFailureHandler(LoginHolder loginHolder, SecurityConfig securityConfig) {
        this.loginHolder = loginHolder;
        this.securityConfig = securityConfig;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        Result<?> result;
        Integer status = null;
        Login loginDTO = loginHolder.get(true);
        String key = SecurityConstant.LOGIN_ERR_NUM_KEY.concat(":").concat(loginDTO.getAccount());
        int errNum = Optional.ofNullable(RedisHelper.getInstance().get(key, Integer.class)).orElse(0);
        if (e instanceof UsernameNotFoundException) {
            result = Result.fail(ResultCode.LOGIN_PASSWORD_ERROR);
        } else if (e instanceof BadCredentialsException) {
            result = Result.fail(ResultCode.LOGIN_PASSWORD_ERROR);
            status = 2;
            errNum++;
        } else if (e instanceof DisabledException || e instanceof LockedException) {
            result = Result.fail(ResultCode.LOGIN_DISABLED);
            status = 4;
            errNum++;
        } else if (e instanceof VerifyCodeException) {
            result = Result.fail(ResultCode.LOGIN_VERIFICATION_ERROR);
            errNum++;
        } else if (e instanceof LoginException loginException) {
            if (CharSequenceUtil.isNotBlank(e.getMessage())) {
                result = Result.fail(loginException.resultCode, e.getMessage());
            } else {
                result = Result.fail(loginException.resultCode);
            }
            status = 5;
        } else if (e instanceof PwdErrorException) {
            result = Result.fail(ResultCode.LOGIN_PASSWORD_FREQUENCY_ERROR);
            status = 3;
            errNum++;
        } else {
            result = Result.error();
        }
        // 刷新错误次数
        RedisHelper.getInstance().set(key, errNum, securityConfig.getErrTime());
        httpServletResponse.setStatus(result.httpStatus().value());
        ServletUtil.write(httpServletResponse, result.toString(), MediaType.APPLICATION_JSON_VALUE);
        if (Objects.nonNull(status)) {
            loginDTO.setStatus(status);
            EventManager.trigger(CoreEvent.E_LOGIN, loginDTO);
        }
    }
}
