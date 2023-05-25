package com.izhimu.seas.healthy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 内存信息视图
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class Memory implements Serializable {

    /**
     * 总数
     */
    private Long total;

    private String totalStr;

    /**
     * 已使用
     */
    private Long used;

    private String usedStr;

    /**
     * 交换分区总数
     */
    private Long swapTotal;

    private String swapTotalStr;

    /**
     * 交换分区已使用
     */
    private Long swapUsed;

    private String swapUsedStr;

    /**
     * 虚拟内存最大数量
     */
    private Long virtualMax;

    private String virtualMaxStr;

    /**
     * 虚拟内存已使用
     */
    private Long virtualInUse;

    private String virtualInUseStr;
}
