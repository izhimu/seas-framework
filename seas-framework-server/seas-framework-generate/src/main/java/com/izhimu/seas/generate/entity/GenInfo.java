package com.izhimu.seas.generate.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 代码生成信息
 *
 * @author haoran
 */
@Data
public class GenInfo implements Serializable {

    /**
     * 数据源ID
     */
    private String sourceId;
    /**
     * 模板ID
     */
    private String templateId;
    /**
     * 作者
     */
    private String author;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表说明
     */
    private String tableDesc;
    /**
     * 表前缀
     */
    private String tablePrefix;
    /**
     * 类名
     */
    private String className;
    /**
     * 字段信息
     */
    private List<GenFieldInfo> fieldList;
}
