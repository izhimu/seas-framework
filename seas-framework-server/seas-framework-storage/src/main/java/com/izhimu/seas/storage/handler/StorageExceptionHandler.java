package com.izhimu.seas.storage.handler;

import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.web.ResultCode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.io.Serializable;

/**
 * 全局异常处理
 *
 * @author haoran
 * @version v1.0
 */
@Order(-200)
@ControllerAdvice
@ConditionalOnClass({ControllerAdvice.class})
public class StorageExceptionHandler {

    /**
     * 找不到页面
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = FileNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Result<Serializable>> noHandlerFoundExceptionHandler() {
        return Result.fail(ResultCode.NOT_FOUND).buildResponseEntity();
    }
}
