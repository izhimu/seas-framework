package com.izhimu.seas.base.param;

import com.izhimu.seas.mybatis.annotation.OrderBy;
import com.izhimu.seas.mybatis.annotation.Search;
import com.izhimu.seas.mybatis.enums.SearchType;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典参数
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysDictParam implements Serializable {

    @OrderBy
    private Integer sort;

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
}
