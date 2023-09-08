package com.izhimu.seas.security.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.security.holder.LoginHolder;
import com.izhimu.seas.security.service.SecurityService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.izhimu.seas.security.constant.SecurityConstant.DEF_USER;

/**
 * 安全服务接口实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
public class DefSecurityServiceImpl implements SecurityService {

    private final String DEF_PWD;

    public DefSecurityServiceImpl() {
        DEF_PWD = RandomUtil.randomString(16);
        log.info(LogUtil.format("SecurityService", "Use def user", Map.of("User", DEF_USER, "Password", DEF_PWD)));
    }

    @Resource
    private LoginHolder loginHolder;

    @Override
    public User loadUserByUsername(String username) {
        User user = new User();
        user.setId(IdUtil.getSnowflakeNextId());
        user.setUserAccount(DEF_USER);
        user.setUserCertificate(BCrypt.hashpw(DEF_PWD));
        user.setLogicDel(0);
        user.setStatus(0);
        user.setTypeCode(0);
        user.setNickName(DEF_USER);
        user.setLogin(loginHolder.get(false));
        return user;
    }
}
