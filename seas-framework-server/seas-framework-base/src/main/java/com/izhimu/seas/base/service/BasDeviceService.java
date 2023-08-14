package com.izhimu.seas.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.base.entity.BasDevice;

import java.util.List;

/**
 * 用户设备服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasDeviceService extends IService<BasDevice> {

    /**
     * 根据设备号获取设备信息
     *
     * @param deviceCode 设备号
     * @return 设备信息集合
     */
    List<BasDevice> findByCode(String deviceCode);
}
