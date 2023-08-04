package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 组织权限实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_BAS_AUTH_ORG")
public class BasAuthOrg extends IdEntity {

    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 组织列表
     */
    @ViewIgnore
    @TableField(exist = false)
    private List<Long> orgIds;
}
