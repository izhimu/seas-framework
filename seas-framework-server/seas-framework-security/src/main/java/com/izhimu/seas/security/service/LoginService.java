package com.izhimu.seas.security.service;

import com.izhimu.seas.core.entity.Login;

/**
 * 登录服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface LoginService {

    /**
     * 登录
     *
     * @param dto Login
     * @return Login
     */
    Login login(Login dto);

    /**
     * 退出
     */
    void logout();
}
