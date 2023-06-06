package com.izhimu.seas.core.enums;

/**
 * @author haoran
 * @version v1.0
 */
public enum TimerType {

    CRON(0),
    TIMING(1),
    INTERVAL(2);

    private final int type;

    TimerType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
