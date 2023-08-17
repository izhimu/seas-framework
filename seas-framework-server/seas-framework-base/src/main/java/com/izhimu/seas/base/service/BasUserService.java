package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.BasUser;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.service.IBaseService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasUserService extends IBaseService<BasUser> {

    /**
     * 创建用户
     *
     * @param basUser SysUserDTO
     */
    void saveUser(BasUser basUser);

    /**
     * 更新用户
     *
     * @param basUser SysUserDTO
     */
    void updateUser(BasUser basUser);

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
    List<BasUser> findUserList();

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
    BasUser get(Long id);

    /**
     * 获取当前登录用户
     *
     * @return User
     */
    BasUser getCurrentUser();

    /**
     * 根据ID集合查询用户名映射
     *
     * @param ids ID集合
     * @return 用户名映射
     */
    Map<Long, String> findUsernameMap(Collection<Long> ids);
}
