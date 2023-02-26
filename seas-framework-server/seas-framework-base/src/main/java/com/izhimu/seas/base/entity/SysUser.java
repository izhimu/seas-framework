package com.izhimu.seas.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.izhimu.seas.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
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
    private String userName;
    /**
     * 用户性别
     * 0.保密 1.男 2.女
     */
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
    private String mobile;
    /**
     * 邮箱
     */
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
    private Integer status;

    public Integer getStatus() {
        return Objects.nonNull(status) ? status : 0;
    }
}
