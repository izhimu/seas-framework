package com.izhimu.seas.security.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.cache.service.RedisService;
import com.izhimu.seas.captcha.model.Captcha;
import com.izhimu.seas.captcha.service.CaptchaService;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.utils.WebUtil;
import com.izhimu.seas.core.web.ResultCode;
import com.izhimu.seas.security.config.SecurityConfig;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.security.exception.SecurityException;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.security.service.LoginService;
import com.izhimu.seas.security.service.SecurityService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.izhimu.seas.security.constant.SecurityConstant.SESSION_KEY;

/**
 * 登录服务接口实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private EncryptService<EncryptKey, String> encryptService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SecurityService securityService;
    @Resource
    private RedisService redisService;
    @Resource
    private LoginHolder loginHolder;
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private HttpServletRequest request;

    @Override
    public Login login(Login dto) {
        if (Objects.isNull(dto)) {
            throw new SecurityException(ResultCode.LOGIN_ERROR, "登录信息异常");
        }
        dto.setIp(WebUtil.getClientIP(request));
        loginHolder.set(dto);

        // 验证码错误
        Captcha captcha = new Captcha();
        captcha.setToken(dto.getVerifyCodeKey());
        captcha.setCaptchaVerification(dto.getVerifyCode());
        if (!captchaService.verification(captcha)) {
            throw new SecurityException(ResultCode.LOGIN_VERIFICATION_ERROR);
        }

        // 校验密码错误次数
        String errKey = SecurityConstant.LOGIN_ERR_NUM_KEY.concat(":").concat(dto.getAccount());
        int errNum = Optional.ofNullable(redisService.get(errKey, Integer.class)).orElse(0);
        if (errNum >= securityConfig.getErrNum()) {
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_FREQUENCY_ERROR);
        }

        // 核对密码
        User user = securityService.loadUserByUsername(dto.getAccount());
        if (Objects.isNull(user)) {
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_ERROR);
        }
        try {
            dto.setPassword(encryptService.decrypt(dto.getPasswordKey(), dto.getPassword()));
        } catch (Exception e) {
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_ERROR);
        }
        if (!BCrypt.checkpw(dto.getPassword(), user.getUserCertificate())) {
            redisService.set(errKey, ++errNum, securityConfig.getErrTime());
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_ERROR);
        }

        try {
            StpUtil.login(user.getId());
            Login token = createToken(user);
            sessionHandle(user);
            // 清除错误次数
            redisService.del(errKey);
            dto.setStatus(0);
            EventManager.trigger(CoreEvent.E_LOGIN, dto);
            return token;
        } catch (Exception e) {
            StpUtil.logout(user.getId());
            log.error("", e);
        }
        throw new SecurityException(ResultCode.LOGIN_ERROR);
    }

    @Override
    public void logout() {
        StpUtil.logout();
        EventManager.trigger(CoreEvent.E_LOGIN);
    }

    /**
     * 生成Token
     *
     * @param user User
     */
    private Login createToken(User user) {
        Login login = new Login();
        login.setToken(StpUtil.getTokenValue());
        login.setTokenTimeout(StpUtil.getTokenTimeout());
        login.setUserId(user.getId());
        login.setUserName(user.getNickName());
        login.setPwdExpired(false);
        return login;
    }

    /**
     * session信息处理
     *
     * @param user User
     */
    private void sessionHandle(User user) {
        user.setUserCertificate(null);
        Login login = user.getLogin();
        login.setPassword(null);
        login.setPasswordKey(null);
        login.setVerifyCode(null);
        login.setVerifyCodeKey(null);
        StpUtil.getSession().set(SESSION_KEY, user);
    }
}
