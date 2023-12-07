package com.izhimu.seas.generate.handler;

import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * 异常处理
 *
 * @author haoran
 * @version v1.0
 */
@Order(-200)
@ControllerAdvice
@ConditionalOnClass({ControllerAdvice.class})
public class GenerateHandler {

    /**
     * 数据库引擎异常
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = DbEngineException.class)
    @ResponseBody
    public ResponseEntity<Result<Serializable>> dbEngineExceptionHandler(DbEngineException e) {
        return Result.ok(e.getMessage(), null).buildResponseEntity();
    }
}
