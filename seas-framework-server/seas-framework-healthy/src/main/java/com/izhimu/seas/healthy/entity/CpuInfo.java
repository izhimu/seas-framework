package com.izhimu.seas.healthy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * CPU信息视图
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class CpuInfo implements Serializable {

    /**
     * CPU数量
     */
    private Integer total;

    /**
     * 系统使用率
     * %
     */
    private Double sys;

    /**
     * 用户使用率
     * %
     */
    private Double user;

    /**
     * 空闲率
     * %
     */
    private Double free;

    /**
     * 型号
     */
    private String model;
}
