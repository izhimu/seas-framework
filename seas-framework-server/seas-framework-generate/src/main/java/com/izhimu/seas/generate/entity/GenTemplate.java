package com.izhimu.seas.generate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 模板实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_GEN_TEMPLATE")
public class GenTemplate extends IdEntity {

    /**
     * 模板名称
     */
    @Search
    private String templateName;
    /**
     * 模板版本
     */
    @Search
    private String templateVersion;
}
