package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.BaseEntity;
import com.izhimu.seas.data.enums.SearchType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_BAS_MENU")
public class BasMenu extends BaseEntity {

    /**
     * 父级ID
     */
    @Search(type = SearchType.EQUALS)
    private Long parentId;
    /**
     * 排序
     */
    @OrderBy
    private Integer sort;
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
     * 菜单类型
     * 0.菜单 1.按钮
     */
    private Integer menuType;
    /**
     * 菜单图标
     */
    private String menuIcon;
    /**
     * 菜单地址
     */
    @Search
    private String menuUrl;
    /**
     * 菜单组件
     */
    private String menuComponent;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 是否显示
     */
    @Search(type = SearchType.EQUALS)
    private Integer display;
}
