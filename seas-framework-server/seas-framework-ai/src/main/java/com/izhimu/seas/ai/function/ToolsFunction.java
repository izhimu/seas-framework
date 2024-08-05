package com.izhimu.seas.ai.function;

import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

public abstract class ToolsFunction<REQ, RES> implements Function<REQ, RES> {

    public static final String FUNCTION_SYSTEM_MANAGER = "如果调用了一个工具，则响应后严格跟随工具的返回值，无需额外判断。";

    public FunctionCallbackWrapper<REQ, RES> getWrapper() {
        Description description = this.getClass().getAnnotation(Description.class);
        return new FunctionCallbackWrapper.Builder<>(this)
                .withName(this.getClass().getSimpleName())
                .withDescription(description.value())
                .build();
    }
}
