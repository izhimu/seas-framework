package com.izhimu.seas.core.utils;

import com.izhimu.seas.core.entity.User;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (Objects.isNull(principal)) {
            return null;
        }
        return (User) principal;
    }
}
