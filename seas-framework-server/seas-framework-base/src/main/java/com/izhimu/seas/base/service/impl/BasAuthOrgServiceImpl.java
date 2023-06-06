package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasAuthOrg;
import com.izhimu.seas.base.mapper.BasAuthOrgMapper;
import com.izhimu.seas.base.service.BasAuthOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 组织权限服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAuthOrgServiceImpl extends ServiceImpl<BasAuthOrgMapper, BasAuthOrg> implements BasAuthOrgService {
}
