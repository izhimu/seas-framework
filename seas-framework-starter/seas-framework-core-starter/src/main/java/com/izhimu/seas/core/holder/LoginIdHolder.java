package com.izhimu.seas.core.holder;

/**
 * 登录账号Id持有器
 *
 * @author haoran
 * @version v1.0
 */
public class LoginIdHolder {

    private static final ThreadLocal<Object> THREAD_LOCAL = new InheritableThreadLocal<>();

    private LoginIdHolder() {
    }

    public static Object get() {
        return THREAD_LOCAL.get();
    }

    public static void set(Object loginId) {
        THREAD_LOCAL.set(loginId);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
