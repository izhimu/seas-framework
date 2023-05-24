package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.entity.BaseEntity;
import com.izhimu.seas.data.enums.SearchType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * 用户实体
 *
 * @author haoran
 * @version v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("SEAS_SYS_USER")
public class SysUser extends BaseEntity {

    /**
     * 用户昵称
     */
    @Search
    private String userName;
    /**
     * 用户性别
     * 0.保密 1.男 2.女
     */
    @Search(type = SearchType.EQUALS)
    private Integer userSex;
    /**
     * 用户生日
     */
    private LocalDate birthday;
    /**
     * 个人签名
     */
    private String signature;
    /**
     * 手机号
     */
    @Search
    private String mobile;
    /**
     * 邮箱
     */
    @Search
    private String email;
    /**
     * 头像
     */
    private Long face;
    /**
     * 小头像
     */
    private Long faceSmall;
    /**
     * 状态
     * 0.正常 1.禁用
     */
    @Search(type = SearchType.EQUALS)
    private Integer status;
    // View
    /**
     * 账号
     */
    @TableField(exist = false)
    private List<SysAccount> accounts;
    // Param
    @TableId
    @OrderBy
    private Long id;

    public Integer getStatus() {
        return Objects.nonNull(status) ? status : 0;
    }
}
