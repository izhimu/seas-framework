package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.base.mapper.BasUserRoleMapper;
import com.izhimu.seas.base.service.BasUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户角色中间服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasUserRoleServiceImpl extends ServiceImpl<BasUserRoleMapper, BasUserRole> implements BasUserRoleService {
}
