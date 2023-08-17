package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.data.service.IMetaObjectService;
import com.izhimu.seas.security.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 自动填充服务接口
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class MetaObjectServiceImpl implements IMetaObjectService {

    @Override
    public Long getUserId() {
        User user = SecurityUtil.getUser();
        if (Objects.isNull(user)){
            return null;
        }
        return user.getId();
    }
}
