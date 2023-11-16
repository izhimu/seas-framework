package com.izhimu.seas.generate.db.exception;

import lombok.Getter;

/**
 * 数据库引擎异常
 *
 * @author haoran
 * @version v1.0
 */
@Getter
@SuppressWarnings("unused")
public class DbEngineException extends RuntimeException {

    public DbEngineException(String msg) {
        super(msg);
    }
}
