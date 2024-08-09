package com.izhimu.seas.security.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import com.izhimu.seas.core.web.interceptor.IExpansionInterceptor;
import com.izhimu.seas.security.config.SecurityConfig;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if (Objects.equals(BasicErrorController.class, ((HandlerMethod) handler).getBeanType())) {
            return true;
        }
        StpUtil.checkLogin();
        return true;
    }

    @Override
    public String[] excludePath() {
        List<String> excludePath = config.getExcludePath();
        excludePath.add("/security/**");
        excludePath.add("/captcha/**");
        return ArrayUtil.toArray(excludePath, String.class);
    }
}
