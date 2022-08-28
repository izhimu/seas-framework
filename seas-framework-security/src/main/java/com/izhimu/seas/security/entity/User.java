package com.izhimu.seas.security.entity;

import com.izhimu.seas.security.dto.LoginDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * 用户信息
 *
 * @author 13346
 * @version v1.0
 */
@Data
public class User implements UserDetails {
    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String nickName;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userCertificate;
    /**
     * 用户类型
     */
    private Integer typeCode;
    /**
     * 状态
     * 0、正常 1、过期 2、锁定 3、密码过期
     */
    private Integer status;
    /**
     * 逻辑删除
     */
    private Integer logicDel;
    /**
     * 是否为超级管理员
     */
    private Boolean isSuper;
    /**
     * 登录信息
     */
    private LoginDTO login;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.userAccount;
    }

    @Override
    public String getUsername() {
        return this.userCertificate;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !Objects.equals(1, this.status);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(1, this.status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !Objects.equals(1, this.status);
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(0, this.logicDel);
    }
}
