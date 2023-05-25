package com.izhimu.seas.healthy.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 硬盘信息视图
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class Disk implements Serializable {

    private String uuid;

    /**
     * 名称
     */
    private String name;

    /**
     * 卷名
     */
    private String volume;

    /**
     * 文件系统
     */
    private String type;

    /**
     * 容量
     */
    private Long total;

    private String totalStr;

    /**
     * 空闲量
     */
    private Long used;

    private String usedStr;
}
