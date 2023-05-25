package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.SysUser;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

/**
 * 用户服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysUserService extends IBaseService<SysUser> {

    /**
     * 创建用户
     *
     * @param sysUser SysUserDTO
     */
    void saveUser(SysUser sysUser);

    /**
     * 更新用户
     *
     * @param sysUser SysUserDTO
     */
    void updateUser(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param id Long
     */
    void delUser(Long id);

    /**
     * 获取所有用户
     *
     * @return List<SysUser>
     */
    List<SysUser> getUserList();

    /**
     * 模糊查询用户信息
     *
     * @param search 条件
     * @return List<Select < String>>
     */
    List<Select<String>> likeUser(String search);

    /**
     * 根据ID获取
     *
     * @param id Long
     * @return SysUser
     */
    SysUser get(Long id);

    /**
     * 获取当前登录用户
     *
     * @return User
     */
    User getCurrentUser();
}
