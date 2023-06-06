package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 组织架构实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_BAS_ORG")
public class BasOrg extends BaseEntity {

    /**
     * 父级ID
     */
    private Long parentId;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 组织代码
     */
    private String orgCode;
    /**
     * 组织类型
     */
    private Long orgType;
    /**
     * 级别
     */
    private Integer orgLevel;
    /**
     * 组织地址
     */
    private String orgAddress;
    /**
     * 组织负责人
     */
    private String managerName;
    /**
     * 组织负责人联系方式
     */
    private String managerMobile;
    /**
     * 备注
     */
    private String remark;
    /**
     * 排序
     */
    private Integer sort;
}
