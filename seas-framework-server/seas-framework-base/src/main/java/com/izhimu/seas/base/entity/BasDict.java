package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.enums.SearchType;
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
@TableName("SEAS_BAS_DICT")
public class BasDict extends IdEntity {

    /**
     * 父级ID
     */
    @Search(type = SearchType.EQUALS)
    private Long parentId;
    /**
     * 字典名称
     */
    @Search
    private String dictName;
    /**
     * 字典编号
     */
    @Search
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
    @OrderBy
    private Integer sort;
}
