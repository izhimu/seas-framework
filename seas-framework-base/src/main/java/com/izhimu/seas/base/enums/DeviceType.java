package com.izhimu.seas.base.enums;

/**
 * Describe
 *
 * @author haoran
 * @version v1.0
 */
public enum DeviceType {

    /**
     * 网页端
     */
    WEB("WEB"),
    /**
     * 移动端
     */
    MOBILE("MOBILE"),
    /**
     * H5、小程序端
     */
    APPLETS("APPLETS"),
    /**
     * 其他
     */
    OTHER("OTHER");

    private final String value;

    DeviceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
