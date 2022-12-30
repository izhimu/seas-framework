package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织权限实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_AUTH_ORG")
public class SysAuthOrg implements Serializable {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 组织ID
     */
    private Long orgId;
    /**
     * 角色ID
     */
    private Long roleId;
}
