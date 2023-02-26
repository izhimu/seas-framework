package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.utils.SecurityUtil;
import com.izhimu.seas.data.service.IMetaObjectService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        return Optional.ofNullable(SecurityUtil.contextUser()).map(User::getId).orElse(null);
    }
}
