package com.izhimu.seas.healthy.service;

import com.izhimu.seas.healthy.entity.*;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;

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
    Memory getMemory();

    /**
     * 获取传感器相关信息
     *
     * @return {@link oshi.hardware.Sensors Sensors}
     */
    Sensors getSensors();

    /**
     * 获取磁盘相关信息
     *
     * @return {@link HWDiskStore HWDiskStore}
     */
    List<Disk> getDiskStores();

    /**
     * 获取网络相关信息
     *
     * @return {@link NetworkIF NetworkIF}
     */
    List<Network> getNetworkIFs();

    /**
     * 获取系统CPU信息
     *
     * @return {@link cn.hutool.system.oshi.CpuInfo CpuInfo}
     */
    CpuInfo getCpuInfo();
}
