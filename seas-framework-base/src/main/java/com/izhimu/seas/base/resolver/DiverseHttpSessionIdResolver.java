package com.izhimu.seas.base.resolver;

import cn.hutool.core.util.StrUtil;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 多样化SessionID获取
 *
 * @author haoran
 * @version v1.0
 */
public class DiverseHttpSessionIdResolver extends HeaderHttpSessionIdResolver {

    private static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";
    private static final String ATTRIBUTE_TOKEN = "token";

    public DiverseHttpSessionIdResolver() {
        super(HEADER_X_AUTH_TOKEN);
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String headerValue = request.getHeader(HEADER_X_AUTH_TOKEN);
        if (StrUtil.isBlank(headerValue)) {
            headerValue = request.getParameter(ATTRIBUTE_TOKEN);
        }
        return (headerValue != null) ? Collections.singletonList(headerValue) : Collections.emptyList();
    }
}
