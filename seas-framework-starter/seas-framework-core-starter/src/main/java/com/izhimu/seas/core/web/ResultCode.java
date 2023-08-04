package com.izhimu.seas.core.web;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 响应状态码
 *
 * @author haoran
 * @version v1.0
 */
@Getter
public enum ResultCode {
    /**
     * 基础响应码
     */
    SUCCESS("000", "成功", "", HttpStatus.OK),
    FAIL("001", "失败", "失败", HttpStatus.OK),
    DENIED("002", "认证失败", "用户认证失败", HttpStatus.UNAUTHORIZED),
    NO_PERMISSION("003", "暂无权限访问此资源", "用户暂无权限", HttpStatus.FORBIDDEN),
    NOT_FOUND("004", "请求的资源未找到", "资源未找到", HttpStatus.NOT_FOUND),
    WRONG_METHOD("005", "错误的请求方式", "请求方式错误", HttpStatus.METHOD_NOT_ALLOWED),
    ERROR("006", "遇到了未知的异常", "系统错误", HttpStatus.INTERNAL_SERVER_ERROR),

    /**
     * 认证相关
     */
    LOGIN_ERROR("010", "登录失败，请重试", "用户登录异常", HttpStatus.UNAUTHORIZED),
    LOGIN_DISABLED("011", "您的账号被禁用，请联系管理员", "用户账号被禁用", HttpStatus.UNAUTHORIZED),
    LOGIN_PASSWORD_ERROR("012", "用户名或密码错误", "用户名或密码错误", HttpStatus.OK),
    LOGIN_PASSWORD_FREQUENCY_ERROR("013", "用户名或密码错误次数超限，请联系管理员", "用户名或密码错误次数超限", HttpStatus.OK),
    LOGIN_OVERDUE("014", "未登录或登录已过期，请重新登录", "未登录或登录已过期", HttpStatus.UNAUTHORIZED),
    LOGIN_VERIFICATION_ERROR("015", "验证码错误，请重试", "验证码错误", HttpStatus.OK),

    /**
     * 验证相关
     */
    VERIFY_ERROR("021", "请求的参数不正确", "请求的参数不正确", HttpStatus.OK);


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
     * HTTP状态码
     */
    private final HttpStatus status;

    ResultCode(String code, String tips, String err, HttpStatus status) {
        this.code = code;
        this.tips = tips;
        this.err = err;
        this.status = status;
    }
}
