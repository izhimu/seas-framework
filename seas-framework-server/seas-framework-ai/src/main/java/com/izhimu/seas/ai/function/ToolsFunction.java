package com.izhimu.seas.ai.function;

import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

public abstract class ToolsFunction<REQ, RES> implements Function<REQ, RES> {

    public static final String FUNCTION_SYSTEM_MANAGER = "If a tool is invoked, the response is strictly followed by the tool's return value, and no additional judgment is required。";

    public FunctionCallbackWrapper<REQ, RES> getWrapper() {
        Description description = this.getClass().getAnnotation(Description.class);
        return new FunctionCallbackWrapper.Builder<>(this)
                .withName(this.getClass().getSimpleName())
                .withDescription(description.value())
                .build();
    }
}
