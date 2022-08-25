package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户组织中间实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_USER_ORG")
public class SysUserOrg implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户ID
     */
    private Long orgId;
}
