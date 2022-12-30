package com.izhimu.seas.mybatis.enums;

/**
 * 搜索类型
 *
 * @author haoran
 * @version v1.0
 */
public enum SearchType {

    /**
     * 等于
     */
    EQUALS,
    /**
     * 模糊
     */
    LIKE,
    /**
     * 左模糊
     */
    LIKE_L,
    /**
     * 右模糊
     */
    LIKE_R,
    /**
     * 包含
     */
    IN,
    /**
     * 不包含
     */
    NOT_IN,
    /**
     * 大于
     */
    GT,
    /**
     * 大于等于
     */
    GE,
    /**
     * 小于
     */
    LT,
    /**
     * 小于等于
     */
    LE
}
