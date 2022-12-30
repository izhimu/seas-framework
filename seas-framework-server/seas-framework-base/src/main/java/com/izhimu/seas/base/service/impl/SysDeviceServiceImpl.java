package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.SysDevice;
import com.izhimu.seas.base.mapper.SysDeviceMapper;
import com.izhimu.seas.base.service.SysDeviceService;
import org.springframework.stereotype.Service;

/**
 * 用户设备服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class SysDeviceServiceImpl extends ServiceImpl<SysDeviceMapper, SysDevice> implements SysDeviceService {
}
