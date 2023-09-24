package com.izhimu.seas.security.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.izhimu.seas.core.web.interceptor.IExpansionInterceptor;
import com.izhimu.seas.security.config.SecurityConfig;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 安全拦截器
 *
 * @author haoran
 * @version v1.0
 */
@Component
public class SecurityInterceptor implements IExpansionInterceptor {

    @Resource
    private SecurityConfig config;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
        StpUtil.checkLogin();
        return true;
    }

    @Override
    public String[] excludePath() {
        List<String> excludePath = config.getExcludePath();
        int size = excludePath.size();
        String[] paths = new String[size + 2];
        paths[0] = "/security/**";
        paths[1] = "/captcha/**";
        for (int i = 0; i < size; i++) {
            paths[i + 2] = excludePath.get(i);
        }
        return paths;
    }
}
