package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.mybatis.service.IMetaObjectService;
import com.izhimu.seas.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

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
        return SecurityUtil.contextUser().getId();
    }
}
