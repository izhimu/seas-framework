package com.izhimu.seas.core.web;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.izhimu.seas.core.utils.JsonUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author haoran
 * @version v1.0
 */
@Getter
@SuppressWarnings("unused")
public class Result<T> implements Serializable {

    /**
     * 跟踪ID
     */
    private final String trackId;

    /**
     * 状态码
     */
    private final String code;

    /**
     * 提示信息
     */
    private final String tips;

    /**
     * 错误信息
     */
    private final String err;

    /**
     * 数据
     */
    private final T data;

    /**
     * 响应码
     */
    @JsonIgnore
    private final ResultCode resultCode;

    private Result(String code, String tips, String err, T data, ResultCode resultCode) {
        this.trackId = IdUtil.getSnowflakeNextIdStr();
        this.code = code;
        this.tips = tips;
        this.err = err;
        this.data = data;
        this.resultCode = resultCode;
    }

    @Override
    public String toString() {
        return JsonUtil.toJsonStr(this);
    }

    public HttpStatus httpStatus() {
        return this.resultCode.getStatus();
    }

    public ResponseEntity<Result<T>> buildResponseEntity() {
        return new ResponseEntity<>(this, this.httpStatus());
    }

    public static <T> Result<T> of(ResultCode code, String tips, String err, T data) {
        return new Result<>(code.getCode(), tips, err, data, code);
    }

    public static <T> Result<T> of(ResultCode code, String tips, T data) {
        return new Result<>(code.getCode(), tips, code.getErr(), data, code);
    }

    public static <T> Result<T> of(ResultCode code, T data) {
        return new Result<>(code.getCode(), code.getTips(), code.getErr(), data, code);
    }

    public static <T> Result<T> of(ResultCode code) {
        return of(code, null);
    }

    public static <T> Result<T> ok() {
        return of(ResultCode.SUCCESS);
    }

    public static <T> Result<T> ok(T data) {
        return of(ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> ok(String tips, T data) {
        return of(ResultCode.SUCCESS, tips, data);
    }

    public static <T> Result<T> fail() {
        return of(ResultCode.FAIL);
    }

    public static <T> Result<T> fail(String tips) {
        return of(ResultCode.FAIL, tips, null);
    }

    public static <T> Result<T> fail(String tips, String err) {
        return of(ResultCode.FAIL, tips, err, null);
    }

    public static <T> Result<T> fail(ResultCode code) {
        return of(code);
    }

    public static <T> Result<T> fail(ResultCode code, String tips) {
        return of(code, tips, null);
    }

    public static <T> Result<T> fail(ResultCode code, String tips, String err) {
        return of(code, tips, err, null);
    }

    public static <T> Result<T> error() {
        return of(ResultCode.ERROR);
    }
}
