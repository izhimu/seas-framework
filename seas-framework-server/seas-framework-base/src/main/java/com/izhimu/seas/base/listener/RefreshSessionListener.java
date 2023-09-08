package com.izhimu.seas.base.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.base.service.BasAuthMenuService;
import com.izhimu.seas.base.service.BasAuthOrgService;
import com.izhimu.seas.base.service.BasUserRoleService;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.entity.RefreshSession;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.enums.RefreshSessionType;
import com.izhimu.seas.core.event.CoreEvent;
import com.izhimu.seas.core.event.EventListener;
import com.izhimu.seas.core.event.IEventListener;
import com.izhimu.seas.security.service.SecurityService;
import com.izhimu.seas.security.util.SecurityUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 会话刷新监听器
 *
 * @author haoran
 * @version v1.0
 */
@EventListener(value = CoreEvent.E_SESSION_REFRESH, async = true)
public class RefreshSessionListener implements IEventListener {

    private final SecurityService securityService;
    private final BasUserRoleService userRoleService;
    private final BasAuthOrgService authOrgService;
    private final BasAuthMenuService authMenuService;


    public RefreshSessionListener() {
        securityService = SpringUtil.getBean(SecurityService.class);
        userRoleService = SpringUtil.getBean(BasUserRoleService.class);
        authOrgService = SpringUtil.getBean(BasAuthOrgService.class);
        authMenuService = SpringUtil.getBean(BasAuthMenuService.class);
    }

    @Override
    public boolean onEvent(Object data) {
        if (data instanceof RefreshSession refresh) {
            if (RefreshSessionType.USER.equals(refresh.getType())) {
                refreshByUser(refresh.getId());
            } else if (RefreshSessionType.ROLE.equals(refresh.getType())) {
                refreshByRole(refresh.getId());
            } else if (RefreshSessionType.ORG.equals(refresh.getType())) {
                refreshByOrg(refresh.getId());
            } else if (RefreshSessionType.MENU.equals(refresh.getType())) {
                refreshByMenu(refresh.getId());
            }
        }
        return true;
    }

    private void refreshByUser(Serializable id) {
        User user = SecurityUtil.getUser(id);
        if (Objects.nonNull(user)) {
            Login login = user.getLogin();
            User newUser = securityService.loadUser(login);
            newUser.setUserCertificate(null);
            newUser.setLogin(login);
            SecurityUtil.setUser(id, newUser);
        }
    }

    private void refreshByRole(Serializable id) {
        Set<Long> userId = userRoleService.findUserIdByRoleIdDistinct((Long) id);
        userId.forEach(this::refreshByUser);
    }

    private void refreshByOrg(Serializable id) {
        Set<Long> roleId = authOrgService.findRoleIdByOrgIdDistinct((Long) id);
        roleId.forEach(this::refreshByRole);
    }

    private void refreshByMenu(Serializable id) {
        Set<Long> roleId = authMenuService.findRoleIdByMenuIdDistinct((Long) id);
        roleId.forEach(this::refreshByRole);
    }
}
