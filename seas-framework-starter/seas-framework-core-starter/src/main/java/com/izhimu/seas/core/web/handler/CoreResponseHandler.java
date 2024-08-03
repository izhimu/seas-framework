package com.izhimu.seas.core.web.handler;

import com.izhimu.seas.core.annotation.React;
import com.izhimu.seas.core.web.Result;
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
        // 响应式数据直接返回
        if (returnType.getExecutable().isAnnotationPresent(React.class)) {
            return body;
        }
        if (body instanceof ResponseEntity || returnType.getGenericParameterType() == ResponseEntity.class) {
            return body;
        }
        if (body instanceof Result || returnType.getGenericParameterType() == Result.class) {
            return body;
        }
        if (body instanceof String || returnType.getGenericParameterType() == String.class) {
            return Result.ok(body).toString();
        }
        return Result.ok(body);
    }
}
