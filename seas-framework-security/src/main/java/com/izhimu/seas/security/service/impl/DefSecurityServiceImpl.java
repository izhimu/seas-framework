package com.izhimu.seas.security.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.izhimu.seas.security.entity.User;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.security.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

import static com.izhimu.seas.security.constant.SecurityConstant.DEF_USER;

/**
 * 安全服务接口实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class DefSecurityServiceImpl implements SecurityService {

    private final String DEF_PWD;

    public DefSecurityServiceImpl() {
        DEF_PWD = RandomUtil.randomString(16);
        log.info("Use def user : {} - {}", DEF_USER, DEF_PWD);
    }

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private LoginHolder loginHolder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setId(IdUtil.getSnowflakeNextId());
        user.setUserAccount(DEF_USER);
        user.setUserCertificate(passwordEncoder.encode(DEF_PWD));
        user.setLogicDel(0);
        user.setStatus(0);
        user.setTypeCode(0);
        user.setNickName(DEF_USER);
        user.setLogin(loginHolder.get(false));
        return user;
    }
}
