package com.izhimu.seas.security.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.cache.helper.RedisHelper;
import com.izhimu.seas.security.constant.SecurityConstant;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.core.web.ResultCode;
import com.izhimu.seas.security.dto.LoginDTO;
import com.izhimu.seas.security.entity.EncryptKey;
import com.izhimu.seas.security.exception.LoginException;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.security.config.LoginConfig;
import com.izhimu.seas.security.service.EncryptService;
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
    private final LoginHolder loginHolder;
    private final LoginConfig loginConfig;

    public CustomAuthenticationFilter(EncryptService<EncryptKey, String> encryptService, LoginHolder loginHolder, LoginConfig loginConfig) {
        super(new AntPathRequestMatcher(SecurityConstant.URL_LOGIN, "POST"));
        this.encryptService = encryptService;
        this.loginHolder = loginHolder;
        this.loginConfig = loginConfig;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginDTO loginDTO;
        try (InputStream is = request.getInputStream()) {
            loginDTO = JsonUtil.toObject(is, LoginDTO.class);
            if (Objects.isNull(loginDTO)) {
                throw new LoginException(ResultCode.LOGIN_ERROR, "登录信息异常");
            }
            loginDTO.setIp(ServletUtil.getClientIP(request));
            loginHolder.set(loginDTO);
        } catch (Exception e) {
            log.error("", e);
            throw new LoginException(ResultCode.LOGIN_ERROR, "登录参数缺失或参数不正确");
        }

        // 校验密码错误次数
        String errKey = SecurityConstant.LOGIN_ERR_NUM_KEY.concat(":").concat(loginDTO.getAccount());
        int errNum = Optional.ofNullable(RedisHelper.getInstance().get(errKey, Integer.class)).orElse(0);
        if (errNum >= loginConfig.getErrNum()) {
            throw new LoginException(ResultCode.LOGIN_PASSWORD_FREQUENCY_ERROR, "用户名或密码错误次数超限");
        }

        // 解密登录凭证
        UsernamePasswordAuthenticationToken authRequest;
        try {
            EncryptKey key = encryptService.getEncryptKey(loginDTO.getPasswordKey());
            authRequest = new UsernamePasswordAuthenticationToken(loginDTO.getAccount(),
                    encryptService.decrypt(key, loginDTO.getPassword()));
        } catch (Exception e) {
            throw new BadCredentialsException("账号或密码错误");
        }
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
