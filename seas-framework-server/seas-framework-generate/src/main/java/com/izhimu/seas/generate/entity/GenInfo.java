package com.izhimu.seas.generate.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 代码生成信息
 *
 * @author haoran
 */
@Data
public class GenInfo implements Serializable {

    /**
     * 包名
     */
    private String packageName;
    /**
     * 数据源ID
     */
    private Long sourceId;
    /**
     * 模板ID
     */
    private Long templateId;
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
     * 类名
     */
    private String className;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 路径名
     */
    private String pathName;
    /**
     * 键名
     */
    private String keyName;
    /**
     * 字段信息
     */
    private List<GenFieldInfo> fieldList;
    /**
     * 拓展参数
     */
    private Map<String, Object> ext;
}
