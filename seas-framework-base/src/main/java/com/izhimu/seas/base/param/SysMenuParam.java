package com.izhimu.seas.base.param;

import com.izhimu.seas.mybatis.annotation.OrderBy;
import com.izhimu.seas.mybatis.annotation.Search;
import com.izhimu.seas.mybatis.enums.SearchType;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单参数
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysMenuParam implements Serializable {

    /**
     * 排序
     */
    @OrderBy
    private Integer sort;
    /**
     * 父级ID
     */
    @Search(type = SearchType.EQUALS)
    private Long parentId;
    /**
     * 菜单名称
     */
    @Search
    private String menuName;
    /**
     * 菜单标识
     */
    @Search
    private String menuCode;
    /**
     * 菜单地址
     */
    @Search
    private String menuUrl;
    /**
     * 是否显示
     */
    @Search(type = SearchType.EQUALS)
    private Integer display;
}
