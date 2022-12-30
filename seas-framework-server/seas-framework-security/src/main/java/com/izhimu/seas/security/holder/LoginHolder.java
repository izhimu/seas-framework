package com.izhimu.seas.security.holder;

import com.izhimu.seas.security.dto.LoginDTO;
import org.springframework.stereotype.Component;

/**
 * 登录信息持有器
 *
 * @author haoran
 * @version v1.0
 */
@Component
public class LoginHolder {

    private static final ThreadLocal<LoginDTO> LOGIN = new ThreadLocal<>();

    public void set(LoginDTO dto) {
        LOGIN.set(dto);
    }

    public LoginDTO get(boolean remove) {
        LoginDTO loginDTO = LOGIN.get();
        if (remove) {
            clear();
        }
        return loginDTO;
    }

    public void clear() {
        LOGIN.remove();
    }
}
