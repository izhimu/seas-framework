package com.izhimu.seas.generate.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 字段信息
 *
 * @author haoran
 */
@Data
public class GenFieldInfo implements Serializable {

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 显示名称
     */
    private String showName;
    /**
     * 属性名称
     */
    private String attrName;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * java类型
     */
    private String javaType;
    /**
     * js类型
     */
    private String jsType;
    /**
     * 是否主键
     */
    private Integer isPk;
    /**
     * 可插入
     */
    private Integer insertable;
    /**
     * 可列表显示
     */
    private Integer listable;
    /**
     * 可查询
     */
    private Integer searchable;
    /**
     * 可排序
     */
    private Integer sortable;
    /**
     * 查询类型
     */
    private String searchType;
    /**
     * 控件类型
     */
    private String controlType;
    /**
     * 验证类型
     */
    private String validateType;
}
