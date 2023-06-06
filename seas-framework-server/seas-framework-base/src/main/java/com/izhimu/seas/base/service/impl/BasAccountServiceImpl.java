package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.base.mapper.BasAccountMapper;
import com.izhimu.seas.base.service.BasAccountService;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户账号服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAccountServiceImpl extends BaseServiceImpl<BasAccountMapper, BasAccount> implements BasAccountService {

    @Override
    public List<BasAccount> getByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(BasAccount::getUserId, userId)
                .list();
    }

    @Override
    public List<Select<String>> likeAccount(String search) {
        return this.lambdaQuery()
                .select(BasAccount::getId, BasAccount::getUserAccount)
                .like(BasAccount::getUserAccount, search)
                .list()
                .stream()
                .map(v -> new Select<>(v.getUserAccount(), String.valueOf(v.getId())))
                .toList();
    }
}
