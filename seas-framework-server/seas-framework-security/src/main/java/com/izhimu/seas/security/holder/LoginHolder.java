package com.izhimu.seas.security.holder;

import com.izhimu.seas.core.entity.Login;
import org.springframework.stereotype.Component;

/**
 * 登录信息持有器
 *
 * @author haoran
 * @version v1.0
 */
@Component
public class LoginHolder {

    private static final ThreadLocal<Login> LOGIN = new ThreadLocal<>();

    public void set(Login login) {
        LOGIN.set(login);
    }

    public Login get(boolean remove) {
        Login loginDTO = LOGIN.get();
        if (remove) {
            clear();
        }
        return loginDTO;
    }

    public void clear() {
        LOGIN.remove();
    }
}
