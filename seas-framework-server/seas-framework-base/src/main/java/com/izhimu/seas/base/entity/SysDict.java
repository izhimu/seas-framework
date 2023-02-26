package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_SYS_DICT")
public class SysDict extends IdEntity {

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
