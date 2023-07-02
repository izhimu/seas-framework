package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置信息实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_BAS_CONF")
public class BasConf extends BaseEntity {

    /**
     * 配置名称
     */
    @Search
    private String confName;
    /**
     * 配置标识
     */
    @Search
    private String confKey;
    /**
     * 配置值
     */
    private String confValue;
    /**
     * 备注信息
     */
    private String confInfo;
}
