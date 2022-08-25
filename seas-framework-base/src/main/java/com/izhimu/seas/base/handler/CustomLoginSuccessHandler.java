package com.izhimu.seas.base.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.base.constant.SecurityConstant;
import com.izhimu.seas.base.dto.LoginDTO;
import com.izhimu.seas.base.entity.User;
import com.izhimu.seas.base.holder.LoginHolder;
import com.izhimu.seas.base.service.SysAccountLogService;
import com.izhimu.seas.base.vo.LoginVO;
import com.izhimu.seas.cache.helper.RedisHelper;
import com.izhimu.seas.core.web.Result;
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

    private final SysAccountLogService accountLogService;
    private final LoginHolder loginHolder;

    public CustomLoginSuccessHandler(SysAccountLogService accountLogService, LoginHolder loginHolder) {
        this.accountLogService = accountLogService;
        this.loginHolder = loginHolder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        LoginDTO loginDTO = loginHolder.get(true);
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
        accountLogService.saveLog(loginDTO, 0);
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
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(request.getSession().getId());
        loginVO.setTokenTimeout((long) request.getSession().getMaxInactiveInterval());
        loginVO.setUserId(userDetails.getId());
        loginVO.setPwdExpired(false);
        loginVO.setUserName(userDetails.getNickName());
        Result<LoginVO> result = Result.ok(loginVO);
        ServletUtil.write(response, result.toString(), MediaType.APPLICATION_JSON_VALUE);
    }
}
