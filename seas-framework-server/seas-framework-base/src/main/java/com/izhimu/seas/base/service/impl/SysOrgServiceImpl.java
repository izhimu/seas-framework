package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.SysOrg;
import com.izhimu.seas.base.mapper.SysOrgMapper;
import com.izhimu.seas.base.service.SysOrgService;
import org.springframework.stereotype.Service;

/**
 * 组织架构服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class SysOrgServiceImpl extends ServiceImpl<SysOrgMapper, SysOrg> implements SysOrgService {
}
