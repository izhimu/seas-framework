package com.izhimu.seas.security.service;

import com.izhimu.seas.core.entity.User;

/**
 * 安全服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SecurityService {

    User loadUserByUsername(String username);
}
