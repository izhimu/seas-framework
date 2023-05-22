package com.izhimu.seas.base.service.impl;

import cn.hutool.extra.cglib.CglibUtil;
import com.izhimu.seas.base.entity.SysAccount;
import com.izhimu.seas.base.mapper.SysAccountMapper;
import com.izhimu.seas.base.service.SysAccountService;
import com.izhimu.seas.base.vo.SysAccountVO;
import com.izhimu.seas.core.web.entity.Select;
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
public class SysAccountServiceImpl extends BaseServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {

    @Override
    public List<SysAccountVO> getByUserId(Long userId) {
        return CglibUtil.copyList(this.lambdaQuery()
                .eq(SysAccount::getUserId, userId)
                .list(), SysAccountVO::new);
    }

    @Override
    public List<Select<String>> likeAccount(String search) {
        return this.lambdaQuery()
                .select(SysAccount::getId, SysAccount::getUserAccount)
                .like(SysAccount::getUserAccount, search)
                .list()
                .stream()
                .map(v -> new Select<>(v.getUserAccount(), String.valueOf(v.getId())))
                .toList();
    }
}
