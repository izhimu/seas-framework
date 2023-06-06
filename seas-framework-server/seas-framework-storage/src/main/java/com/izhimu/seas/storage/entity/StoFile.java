package com.izhimu.seas.storage.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 文件信息实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_STO_FILE")
public class StoFile extends IdEntity {

    /**
     * 绑定ID
     */
    @ViewIgnore
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
    @ViewIgnore
    private String storageType;
    /**
     * 删除标记
     * 0，非删除，1，已删除
     */
    @ViewIgnore
    private Integer delTag;
    /**
     * 创建人
     */
    @ViewIgnore
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;
    /**
     * 创建时间
     */
    @ViewIgnore
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @TableId
    @OrderBy(asc = false)
    private Long id;
    // View
    /**
     * 文件地址
     */
    @TableField(exist = false)
    private String fileUrl;
}
