package com.izhimu.seas.healthy.controller;

import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.healthy.service.OsService;
import com.izhimu.seas.healthy.vo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * OS信息控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/healthy/os")
public class OsController {

    @Resource
    private OsService osService;

    /**
     * 获取OS信息
     *
     * @return String
     */
    @OperationLog("系统信息-概览")
    @GetMapping("/info")
    public String getOs() {
        return osService.getOs();
    }

    /**
     * 获取内存相关信息
     *
     * @return {@link MemoryVO MemoryVO}
     */
    @OperationLog("系统信息-内存")
    @GetMapping("/memory")
    public MemoryVO getMemory() {
        return osService.getMemory();
    }

    /**
     * 获取传感器相关信息
     *
     * @return {@link SensorsVO SensorsVO}
     */
    @OperationLog("系统信息-传感器")
    @GetMapping("/sensors")
    public SensorsVO getSensors() {
        return osService.getSensors();
    }

    /**
     * 获取磁盘相关信息
     *
     * @return {@link DiskVO DiskVO}
     */
    @OperationLog("系统信息-硬盘")
    @GetMapping("/disk")
    public List<DiskVO> getDiskStores() {
        return osService.getDiskStores();
    }

    /**
     * 获取网络相关信息
     *
     * @return {@link NetworkVO NetworkVO}
     */
    @OperationLog("系统信息-网络")
    @GetMapping("/network")
    public List<NetworkVO> getNetworkIFs() {
        return osService.getNetworkIFs();
    }

    /**
     * 获取系统CPU信息
     *
     * @return {@link CpuInfoVO CpuInfoVO}
     */
    @OperationLog("系统信息-处理器")
    @GetMapping("/cpu")
    public CpuInfoVO getCpuInfo() {
        return osService.getCpuInfo();
    }
}
