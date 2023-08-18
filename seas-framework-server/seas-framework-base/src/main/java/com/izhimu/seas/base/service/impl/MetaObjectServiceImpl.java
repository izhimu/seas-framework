package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.holder.LoginIdHolder;
import com.izhimu.seas.data.service.IMetaObjectService;
import com.izhimu.seas.security.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自动填充服务接口
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
public class MetaObjectServiceImpl implements IMetaObjectService {

    @Override
    public Long getUserId() {
        Object loginId = LoginIdHolder.get();
        User user = null;
        try {
            user = Objects.isNull(loginId) ? SecurityUtil.getUser() : SecurityUtil.getUser(loginId);
        } catch (Exception e) {
            log.error("", e);
        }
        if (Objects.isNull(user)) {
            return null;
        }
        return user.getId();
    }
}
