package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件信息实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_FILE")
public class SysFile implements Serializable {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 绑定ID
     */
    private Long bindId;
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
     * 存储类型
     */
    private String storageType;
    /**
     * 删除标记
     * 0，非删除，1，已删除
     */
    private Integer delTag;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
}
