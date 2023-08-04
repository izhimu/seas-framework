package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.IdEntity;
import com.izhimu.seas.data.enums.SearchType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户角色实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_BAS_ROLE")
public class BasRole extends IdEntity {

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
    /**
     * 角色名称
     */
    @Search
    private String roleName;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 是否启用
     * 0否、1是
     */
    @Search(type = SearchType.EQUALS)
    private Integer enable;
    /**
     * 优先级
     */
    private Integer sort;
    /**
     * 权限模式
     * 0全部数据权限、1自定义数据权限、2本部门及下级部门数据权限、3本部门数据权限、4仅自己数据权限
     */
    private Integer authType;
    /**
     * 部门级联选择
     * 0否、1是
     */
    private Integer deptCascade;
    // Param
    @TableId
    @OrderBy(asc = false)
    private Long id;
}
