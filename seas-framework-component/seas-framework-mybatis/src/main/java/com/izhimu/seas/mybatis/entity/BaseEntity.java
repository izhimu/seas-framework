package com.izhimu.seas.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;
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
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
    /**
     * 版本
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer logicDel;
}
