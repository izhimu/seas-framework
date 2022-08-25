package com.izhimu.seas.base.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件信息视图
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysFileVO implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String contentType;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     * 单位：b
     */
    private Long fileSize;
    /**
     * 文件后缀
     */
    private String fileSuffix;
    /**
     * 文件地址
     */
    private String fileUrl;
}
