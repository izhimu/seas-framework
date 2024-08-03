package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 主题菜单关联表实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("seas_bas_topic_menu")
public class BasTopicMenu extends IdEntity {

    /**
     * 菜单ID
     */
    private Long menuId;
    /**
     * 主题ID
     */
    private Long topicId;
    /**
     * 选中的，0否，1是
     */
    private Integer isChecked;
    // DTO
    /**
     * 菜单列表
     */
    @ViewIgnore
    @TableField(exist = false)
    private List<Long> menuIds;
    /**
     * 父菜单列表
     */
    @ViewIgnore
    @TableField(exist = false)
    private List<Long> menuPIds;
}
