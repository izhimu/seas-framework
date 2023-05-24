package com.izhimu.seas.core.web.handler;

import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.web.entity.IView;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.Serializable;

/**
 * 统一响应处理
 *
 * @author haoran
 * @version v1.0
 */
@RestControllerAdvice
@ConditionalOnClass({ResponseBodyAdvice.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class CoreResponseHandler implements ResponseBodyAdvice<Serializable> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return !"error".equals(returnType.getExecutable().getName());
    }

    @Override
    public Serializable beforeBodyWrite(Serializable body, @NonNull MethodParameter returnType, @NonNull MediaType selectedContentType, @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if (body instanceof ResponseEntity) {
            return body;
        }
        if (body instanceof Result) {
            return body;
        }
        if (body instanceof String) {
            return Result.ok(body).toString();
        }
        if (body instanceof IView view) {
            return Result.ok(view.toView()).toString();
        }
        return Result.ok(body);
    }
}
