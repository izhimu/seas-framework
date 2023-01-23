package com.izhimu.seas.healthy.service;

import cn.hutool.system.oshi.CpuInfo;
import com.izhimu.seas.healthy.vo.*;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.hardware.Sensors;

import java.util.List;

/**
 * OS信息服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface OsService {

    /**
     * 获取OS信息
     *
     * @return String
     */
    String getOs();

    /**
     * 获取内存相关信息
     *
     * @return {@link GlobalMemory GlobalMemory}
     */
    MemoryVO getMemory();

    /**
     * 获取传感器相关信息
     *
     * @return {@link Sensors Sensors}
     */
    SensorsVO getSensors();

    /**
     * 获取磁盘相关信息
     *
     * @return {@link HWDiskStore HWDiskStore}
     */
    List<DiskVO> getDiskStores();

    /**
     * 获取网络相关信息
     *
     * @return {@link NetworkIF NetworkIF}
     */
    List<NetworkVO> getNetworkIFs();

    /**
     * 获取系统CPU信息
     *
     * @return {@link CpuInfo CpuInfo}
     */
    CpuInfoVO getCpuInfo();
}
