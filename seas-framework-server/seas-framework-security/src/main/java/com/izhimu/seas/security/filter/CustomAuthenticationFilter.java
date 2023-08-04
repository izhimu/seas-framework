package com.izhimu.seas.security.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.cache.helper.RedisHelper;
import com.izhimu.seas.captcha.model.Captcha;
import com.izhimu.seas.captcha.service.CaptchaService;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.web.ResultCode;
import com.izhimu.seas.security.config.SecurityConfig;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.cache.entity.EncryptKey;
import com.izhimu.seas.security.exception.LoginException;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.cache.service.EncryptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

/**
 * 自定义登录过滤器
 * 解析前端传递参数、校验验证码及解析密码
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final EncryptService<EncryptKey, String> encryptService;
    private final CaptchaService captchaService;
    private final LoginHolder loginHolder;
    private final SecurityConfig securityConfig;

    public CustomAuthenticationFilter(EncryptService<EncryptKey, String> encryptService, CaptchaService captchaService, LoginHolder loginHolder, SecurityConfig securityConfig) {
        super(new AntPathRequestMatcher(SecurityConstant.URL_LOGIN, "POST"));
        this.encryptService = encryptService;
        this.captchaService = captchaService;
        this.loginHolder = loginHolder;
        this.securityConfig = securityConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Login loginDTO;
        try (InputStream is = request.getInputStream()) {
            loginDTO = JsonUtil.toObject(is, Login.class);
            if (Objects.isNull(loginDTO)) {
                throw new LoginException(ResultCode.LOGIN_ERROR, "登录信息异常");
            }
            loginDTO.setIp(ServletUtil.getClientIP(request));
            loginHolder.set(loginDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new LoginException(ResultCode.LOGIN_ERROR, "登录参数缺失或参数不正确");
        }

        // 验证码错误
        Captcha captcha = new Captcha();
        captcha.setToken(loginDTO.getVerifyCodeKey());
        captcha.setCaptchaVerification(loginDTO.getVerifyCode());
        if (!captchaService.verification(captcha)) {
            throw new LoginException(ResultCode.LOGIN_VERIFICATION_ERROR, "安全验证未通过");
        }

        // 校验密码错误次数
        String errKey = SecurityConstant.LOGIN_ERR_NUM_KEY.concat(":").concat(loginDTO.getAccount());
        int errNum = Optional.ofNullable(RedisHelper.getInstance().get(errKey, Integer.class)).orElse(0);
        if (errNum >= securityConfig.getErrNum()) {
            throw new LoginException(ResultCode.LOGIN_PASSWORD_FREQUENCY_ERROR, "用户名或密码错误次数超限");
        }

        // 解密登录凭证
        UsernamePasswordAuthenticationToken authRequest;
        try {
            authRequest = new UsernamePasswordAuthenticationToken(loginDTO.getAccount(),
                    encryptService.decrypt(loginDTO.getPasswordKey(), loginDTO.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("账号或密码错误");
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
