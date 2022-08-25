package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户地址实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
@TableName("SEAS_SYS_USER_LOCATION")
public class SysUserLocation implements Serializable {

    /**
     * ID
     */
    @TableId
    private Long id;
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
     * 用户ID
     */
    private Long userId;
    /**
     * 所在国家
     */
    private String currNation;
    /**
     * 所在省区
     */
    private String currProvince;
    /**
     * 所在城市
     */
    private String currCity;
    /**
     * 所在区县
     */
    private String currDistrict;
    /**
     * 行政代码
     */
    private String areaNumber;
    /**
     * 详细地址
     */
    private String location;
    /**
     * 经度
     */
    private Double lng;
    /**
     * 纬度
     */
    private Double lat;
}
