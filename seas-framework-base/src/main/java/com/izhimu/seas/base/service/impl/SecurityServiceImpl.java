package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.base.entity.SysAccount;
import com.izhimu.seas.base.entity.User;
import com.izhimu.seas.base.holder.LoginHolder;
import com.izhimu.seas.base.service.SecurityService;
import com.izhimu.seas.base.service.SysAccountService;
import com.izhimu.seas.base.service.SysUserService;
import com.izhimu.seas.base.vo.SysUserVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 安全服务接口实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Resource
    private SysAccountService accountService;

    @Lazy
    @Resource
    private SysUserService userService;

    @Resource
    private LoginHolder loginHolder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SysAccount> sysAccount = accountService.lambdaQuery()
                .eq(SysAccount::getUserAccount, username)
                .oneOpt();
        if (sysAccount.isEmpty()) {
            return null;
        }
        SysUserVO sysUserVO = userService.get(sysAccount.get().getUserId());
        SysAccount account = sysAccount.get();
        User user = new User();
        user.setId(account.getUserId());
        user.setUserAccount(account.getUserAccount());
        user.setUserCertificate(account.getUserCertificate());
        user.setLogicDel(account.getLogicDel());
        user.setStatus(account.getStatus());
        user.setTypeCode(account.getTypeCode());
        user.setNickName(sysUserVO.getUserName());
        user.setLogin(loginHolder.get(false));
        return user;
    }
}
