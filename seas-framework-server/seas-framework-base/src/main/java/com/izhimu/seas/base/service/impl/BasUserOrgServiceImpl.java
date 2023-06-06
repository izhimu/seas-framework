package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasUserOrg;
import com.izhimu.seas.base.mapper.BasUserOrgMapper;
import com.izhimu.seas.base.service.BasUserOrgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户组织中间服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasUserOrgServiceImpl extends ServiceImpl<BasUserOrgMapper, BasUserOrg> implements BasUserOrgService {
}
