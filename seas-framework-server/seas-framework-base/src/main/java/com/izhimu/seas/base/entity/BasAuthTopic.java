package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.core.annotation.ViewIgnore;
import com.izhimu.seas.data.entity.IdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * 角色主题关联表实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("seas_bas_auth_topic")
public class BasAuthTopic extends IdEntity {

    /**
     * 主题ID
     */
    private Long topicId;
    /**
     * 角色ID
     */
    private Long roleId;
    // DTO

    /**
     * 主题列表
     */
    @ViewIgnore
    @TableField(exist = false)
    private List<Long> topicIds;
}
