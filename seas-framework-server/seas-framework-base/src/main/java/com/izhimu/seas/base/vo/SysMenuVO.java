package com.izhimu.seas.base.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 菜单视图
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysMenuVO implements Serializable {


    private Long id;
    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 菜单名称
     */
    private String menuName;
    /**
     * 菜单标识
     */
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
    private Integer display;
}
