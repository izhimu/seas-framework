package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.SysAccount;
import com.izhimu.seas.base.vo.SysAccountVO;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.data.service.IBaseService;

import java.util.List;

/**
 * 用户账号服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysAccountService extends IBaseService<SysAccount> {

    /**
     * 根据用户ID获取账号列表
     *
     * @param userId 用户ID
     * @return List<SysAccountVO>
     */
    List<SysAccountVO> getByUserId(Long userId);

    /**
     * 模糊查询账号信息
     *
     * @param search 条件
     * @return List<Select < String>>
     */
    List<Select<String>> likeAccount(String search);
}
