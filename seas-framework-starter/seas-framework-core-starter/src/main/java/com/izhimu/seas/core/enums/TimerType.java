package com.izhimu.seas.core.enums;

import lombok.Getter;

/**
 * @author haoran
 * @version v1.0
 */
@Getter
public enum TimerType {

    CRON(0),
    TIMING(1),
    INTERVAL(2);

    private final int type;

    TimerType(int type) {
        this.type = type;
    }
}
