package com.izhimu.seas.core.web.handler;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.text.CharSequenceUtil;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.core.web.Result;
import com.izhimu.seas.core.web.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Order(-100)
@ControllerAdvice
@ConditionalOnClass({ControllerAdvice.class})
public class CoreExceptionHandler {

    /**
     * 找不到页面
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> noHandlerFoundExceptionHandler() {
        return Result.fail(ResultCode.NOT_FOUND).buildResponseEntity();
    }

    /**
     * 错误的请求方式
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> httpRequestMethodNotSupportedExceptionHandler() {
        return Result.fail(ResultCode.WRONG_METHOD).buildResponseEntity();
    }

    /**
     * 请求参数错误
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = ValidateException.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> validationExceptionHandler(Exception e) {
        if (CharSequenceUtil.isNotBlank(e.getMessage())) {
            return Result.fail(ResultCode.VERIFY_ERROR, e.getMessage()).buildResponseEntity();
        } else {
            return Result.fail(ResultCode.VERIFY_ERROR).buildResponseEntity();
        }
    }

    /**
     * 请求参数错误
     *
     * @return ErrResult
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> springValidationExceptionHandler(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        List<String> errors = new ArrayList<>();
        for (ObjectError allError : allErrors) {
            errors.add(allError.getDefaultMessage());
        }
        if (!errors.isEmpty()) {
            return Result.fail(ResultCode.VERIFY_ERROR, String.join(",", errors)).buildResponseEntity();
        } else {
            return Result.fail(ResultCode.VERIFY_ERROR).buildResponseEntity();
        }
    }

    /**
     * 默认处理
     *
     * @param e Exception
     * @return ErrResult
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Result<Object>> defaultErrorHandler(Exception e) {
        //500
        log.error(LogUtil.format("ExceptionHandler", "Error"), e);
        return Result.error().buildResponseEntity();
    }
}
