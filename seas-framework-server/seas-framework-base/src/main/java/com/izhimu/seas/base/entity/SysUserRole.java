package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色中间实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_USER_ROLE")
public class SysUserRole implements Serializable {

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
     * 角色ID
     */
    private Long roleId;
}
