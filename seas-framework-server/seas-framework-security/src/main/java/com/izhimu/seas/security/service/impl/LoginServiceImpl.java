package com.izhimu.seas.security.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.cache.service.CacheService;
import com.izhimu.seas.cache.service.EncryptService;
import com.izhimu.seas.captcha.model.Captcha;
import com.izhimu.seas.captcha.service.CaptchaService;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.event.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.core.utils.IpUtil;
import com.izhimu.seas.core.web.ResultCode;
import com.izhimu.seas.security.config.SecurityConfig;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.security.exception.SecurityException;
import com.izhimu.seas.security.service.LoginService;
import com.izhimu.seas.security.service.SecurityService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


/**
 * 登录服务接口实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final LogWrapper log = LogWrapper.build("LoginService");

    @Resource
    private EncryptService<EncryptKey, String> encryptService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private SecurityService securityService;
    @Resource
    private CacheService cacheService;
    @Resource
    private SecurityConfig securityConfig;
    @Resource
    private HttpServletRequest request;

    @Override
    public Login login(Login dto) {
        if (Objects.isNull(dto)) {
            throw new SecurityException(ResultCode.LOGIN_ERROR, "登录信息异常");
        }
        dto.setIp(IpUtil.getClientIP(request));

        // 验证码错误
        Captcha captcha = new Captcha();
        captcha.setToken(dto.getVerifyCodeKey());
        captcha.setCaptchaVerification(dto.getVerifyCode());
        if (!captchaService.verification(captcha)) {
            throw new SecurityException(ResultCode.LOGIN_VERIFICATION_ERROR);
        }

        User user = securityService.loadUser(dto);
        if (Objects.isNull(user)) {
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_ERROR);
        }
        // 校验账号状态
        if (Objects.equals(1, user.getStatus())) {
            dto.setStatus(4);
            EventManager.trigger(CoreEvent.E_LOGIN, dto);
            throw new SecurityException(ResultCode.LOGIN_DISABLED);
        }

        // 校验密码错误次数
        String errKey = SecurityConstant.LOGIN_ERR_NUM_KEY.concat(":").concat(dto.getAccount());
        int errNum = Optional.ofNullable(cacheService.get(errKey, Integer.class)).orElse(0);
        if (errNum >= securityConfig.getErrNum()) {
            dto.setStatus(3);
            EventManager.trigger(CoreEvent.E_LOGIN, dto);
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_FREQUENCY_ERROR);
        }

        // 核对密码
        try {
            dto.setPassword(encryptService.decrypt(dto.getPasswordKey(), dto.getPassword()));
        } catch (Exception e) {
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_ERROR);
        }
        if (!BCrypt.checkpw(dto.getPassword(), user.getUserCertificate())) {
            cacheService.set(errKey, ++errNum, securityConfig.getErrTime());
            if (errNum >= securityConfig.getErrNum()) {
                cacheService.set(
                        SecurityConstant.LOGIN_ERR_USER_KEY.concat(":").concat(String.valueOf(user.getId())),
                        1,
                        securityConfig.getErrTime()
                );
            }
            dto.setStatus(2);
            EventManager.trigger(CoreEvent.E_LOGIN, dto);
            throw new SecurityException(ResultCode.LOGIN_PASSWORD_ERROR);
        }

        try {
            StpUtil.login(user.getId());
            Login token = createToken(user);
            sessionHandle(user);
            // 清除错误次数
            cacheService.del(errKey);
            dto.setStatus(0);
            EventManager.trigger(CoreEvent.E_LOGIN, dto);
            return token;
        } catch (Exception e) {
            StpUtil.logout(user.getId());
            log.error(e);
        }
        dto.setStatus(5);
        EventManager.trigger(CoreEvent.E_LOGIN, dto);
        throw new SecurityException(ResultCode.LOGIN_ERROR);
    }

    @Override
    public void logout() {
        User user = StpUtil.getSession().getModel(SaSession.USER, User.class);
        Login login = user.getLogin();
        login.setStatus(1);
        EventManager.trigger(CoreEvent.E_LOGOUT, login);
        StpUtil.logout();
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
        StpUtil.getSession().set(SaSession.USER, user);
    }
}
