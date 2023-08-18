package com.izhimu.seas.security.util;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.izhimu.seas.core.entity.DataPermission;
import com.izhimu.seas.core.entity.User;
import lombok.experimental.UtilityClass;

/**
 * 安全工具类
 *
 * @author haoran
 * @version v1.0
 */
@UtilityClass
public class SecurityUtil {

    public static User getUser() {
        return StpUtil.getSession().getModel(SaSession.USER, User.class);
    }

    public static User getUser(Object loginId) {
        return StpUtil.getSessionByLoginId(loginId, false).getModel(SaSession.USER, User.class);
    }

    public static void setUser(User user) {
        StpUtil.getSession().set(SaSession.USER, user);
    }

    public static void setUser(Object loginId, User user) {
        StpUtil.getSessionByLoginId(loginId, false).set(SaSession.USER, user);
    }

    public static DataPermission getPermission() {
        return getUser().getDataAuth();
    }

    public static boolean isSuper() {
        return getUser().getIsSuper();
    }
}
