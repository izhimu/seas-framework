package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户组织中间实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_SYS_USER_ORG")
public class SysUserOrg extends IdEntity {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户ID
     */
    private Long orgId;
}
