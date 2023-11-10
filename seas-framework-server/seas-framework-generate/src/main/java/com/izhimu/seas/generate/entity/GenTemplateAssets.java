package com.izhimu.seas.generate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板资源实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_GEN_TEMPLATE_ASSETS")
public class GenTemplateAssets extends IdEntity {

    /**
     * 模板ID
     */
    private Long templateId;
    /**
     * 资源名称
     */
    private String assetsName;
    /**
     * 资源数据
     */
    private Object assetsData;
    /**
     * 输出路径
     */
    private String outPath;
}
