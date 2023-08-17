package com.izhimu.seas.security.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.izhimu.seas.core.web.interceptor.IExpansionInterceptor;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * 安全拦截器
 *
 * @author haoran
 * @version v1.0
 */
@Component
public class SecurityInterceptor implements IExpansionInterceptor {

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        StpUtil.checkLogin();
        return true;
    }

    @Override
    public String[] excludePath() {
        return new String[]{"/security/**", "/captcha/**"};
    }
}
