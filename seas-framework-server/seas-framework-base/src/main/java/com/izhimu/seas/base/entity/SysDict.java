package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_DICT")
public class SysDict implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 字典名称
     */
    private String dictName;
    /**
     * 字典编号
     */
    private String dictCode;
    /**
     * 是否固定
     * 0.不是 1.是
     */
    private Integer fixed;
    /**
     * 是否有子项
     * 0.否 1.是
     */
    private Integer hasChildren;
    /**
     * 排序
     */
    private Integer sort;
}
