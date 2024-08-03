package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 主题表实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("seas_bas_topic")
public class BasTopic extends BaseEntity {

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 主题名称
     */
    @Search
    private String topicName;
    /**
     * 主题标识
     */
    @Search
    private String topicCode;
    /**
     * 首页地址
     */
    private String indexUrl;
    /**
     * 备注
     */
    private String remarks;
    // VO
    /**
     * 菜单ID列表
     */
    @TableField(exist = false)
    private List<Long> menuIds;
}
