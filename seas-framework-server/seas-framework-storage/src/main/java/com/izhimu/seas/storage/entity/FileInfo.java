package com.izhimu.seas.storage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件信息
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class FileInfo implements Serializable {

    /**
     * 文件名称
     */
    private String name;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * 大小
     */
    private Long size;
    /**
     * MIME类型
     */
    private String contentType;
    /**
     * 页数
     */
    private Integer pageCount;
    /**
     * 宽度
     */
    private Double width;
    /**
     * 高度
     */
    private Double height;
}
