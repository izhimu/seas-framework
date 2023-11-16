package com.izhimu.seas.generate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.enums.SearchType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据源实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_GEN_DATASOURCE")
public class GenDatasource extends IdEntity {

    /**
     * 数据源名称
     */
    @Search
    private String dsName;
    /**
     * 数据源类型
     */
    @Search(type = SearchType.EQUALS)
    private String dsType;
    /**
     * 数据源地址
     */
    @Search
    private String dsUrl;
    /**
     * 数据源用户名
     */
    private String dsUser;
    /**
     * 数据源密码
     */
    private String dsPwd;
    /**
     * 备注
     */
    private String remark;
    /**
     * 数据库名称
     */
    @Search
    private String dbName;
}
