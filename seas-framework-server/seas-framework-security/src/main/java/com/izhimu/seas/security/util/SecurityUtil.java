package com.izhimu.seas.security.util;

import cn.dev33.satoken.stp.StpUtil;
import com.izhimu.seas.core.entity.User;
import lombok.experimental.UtilityClass;

import static com.izhimu.seas.security.constant.SecurityConstant.SESSION_KEY;

/**
 * 安全工具类
 *
 * @author haoran
 * @version v1.0
 */
@UtilityClass
public class SecurityUtil {

    public static User getUser() {
        return StpUtil.getSession().getModel(SESSION_KEY, User.class);
    }
}
