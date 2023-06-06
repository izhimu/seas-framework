package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户角色中间实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_BAS_USER_ROLE")
public class BasUserRole extends IdEntity {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
    // DTO
    /**
     * 用户列表
     */
    @ViewIgnore
    @TableField(exist = false)
    private List<Long> userIds;
}
