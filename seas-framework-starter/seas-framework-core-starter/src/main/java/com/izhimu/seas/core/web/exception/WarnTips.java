package com.izhimu.seas.core.web.exception;

/**
 * 警告提示
 *
 * @author haoran
 * @version v1.0
 */
public class WarnTips extends RuntimeException {

    public WarnTips(String message) {
        super(message);
    }
}
