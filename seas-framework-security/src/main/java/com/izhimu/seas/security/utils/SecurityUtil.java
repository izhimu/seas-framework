package com.izhimu.seas.security.utils;

import com.izhimu.seas.security.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具
 *
 * @author haoran
 * @version v1.0
 */
@UtilityClass
public class SecurityUtil {

    /**
     * 获取当前用户
     *
     * @return User
     */
    public static User contextUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
