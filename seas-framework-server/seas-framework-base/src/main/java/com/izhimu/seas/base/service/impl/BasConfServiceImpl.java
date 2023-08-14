package com.izhimu.seas.base.service.impl;

import com.izhimu.seas.base.entity.BasConf;
import com.izhimu.seas.base.mapper.BasConfMapper;
import com.izhimu.seas.base.service.BasConfService;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * 配置信息服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasConfServiceImpl extends BaseServiceImpl<BasConfMapper, BasConf> implements BasConfService {

    @Override
    public String getValueByKey(String key) {
        Optional<BasConf> basConfOpt = this.lambdaQuery().eq(BasConf::getConfKey, key).oneOpt();
        return basConfOpt.map(BasConf::getConfValue).orElse(null);
    }

    @Override
    public boolean usableKey(Long id, String key) {
        return !this.lambdaQuery()
                .eq(BasConf::getConfKey, key)
                .ne(Objects.nonNull(id), BasConf::getId, id)
                .exists();
    }
}
