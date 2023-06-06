package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.base.mapper.BasOrgMapper;
import com.izhimu.seas.base.service.BasOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组织架构服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasOrgServiceImpl extends ServiceImpl<BasOrgMapper, BasOrg> implements BasOrgService {
}
