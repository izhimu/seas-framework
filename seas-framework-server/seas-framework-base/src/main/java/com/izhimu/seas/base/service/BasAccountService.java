package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.service.IBaseService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户账号服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasAccountService extends IBaseService<BasAccount> {

    /**
     * 根据用户ID获取账号列表
     *
     * @param userId 用户ID
     * @return List<SysAccount>
     */
    List<BasAccount> findByUserId(Long userId);

    /**
     * 根据账号获取账号信息
     *
     * @param account 账号
     * @return BasAccount
     */
    BasAccount getByAccount(String account);

    /**
     * 模糊查询账号信息
     *
     * @param search 条件
     * @return List<Select < String>>
     */
    List<Select<String>> likeAccount(String search);

    /**
     * 根据ID集合查询账号映射
     *
     * @param ids ID集合
     * @return 账号映射
     */
    Map<Long, String> findAccountMap(Collection<Long> ids);

    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return boolean
     */
    boolean removeByUserId(Long userId);

    /**
     * 根据用户ID和排除的ID集合删除
     *
     * @param userId 用户ID
     * @param ids    排除的ID集合
     * @return boolean
     */
    boolean removeByUserIdAndNotInId(Long userId, List<Long> ids);

    /**
     * 检查账号是否可用
     *
     * @param account 账号信息
     * @return true可用
     */
    boolean checkAccount(BasAccount account);
}
