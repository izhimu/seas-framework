package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.SysUserLocation;
import com.izhimu.seas.base.mapper.SysUserLocationMapper;
import com.izhimu.seas.base.service.SysUserLocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户地址服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserLocationServiceImpl extends ServiceImpl<SysUserLocationMapper, SysUserLocation> implements SysUserLocationService {
}
