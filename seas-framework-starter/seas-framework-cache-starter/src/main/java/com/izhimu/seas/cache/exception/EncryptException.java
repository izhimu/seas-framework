package com.izhimu.seas.cache.exception;

/**
 * 加密异常
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public class EncryptException extends RuntimeException {

    public EncryptException() {
    }

    public EncryptException(String message) {
        super(message);
    }
}
