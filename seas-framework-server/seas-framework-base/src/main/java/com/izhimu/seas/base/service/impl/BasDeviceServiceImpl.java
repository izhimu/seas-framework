package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasDevice;
import com.izhimu.seas.base.mapper.BasDeviceMapper;
import com.izhimu.seas.base.service.BasDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户设备服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasDeviceServiceImpl extends ServiceImpl<BasDeviceMapper, BasDevice> implements BasDeviceService {
    @Override
    public List<BasDevice> findByCode(String deviceCode) {
        return this.lambdaQuery()
                .eq(BasDevice::getDeviceCode, deviceCode)
                .list();
    }
}
