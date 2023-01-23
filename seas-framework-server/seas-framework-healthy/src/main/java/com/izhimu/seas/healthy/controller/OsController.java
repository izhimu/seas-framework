package com.izhimu.seas.healthy.controller;

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
    @GetMapping("/info")
    public String getOs() {
        return osService.getOs();
    }

    /**
     * 获取内存相关信息
     *
     * @return {@link MemoryVO MemoryVO}
     */
    @GetMapping("/memory")
    public MemoryVO getMemory() {
        return osService.getMemory();
    }

    /**
     * 获取传感器相关信息
     *
     * @return {@link SensorsVO SensorsVO}
     */
    @GetMapping("/sensors")
    public SensorsVO getSensors() {
        return osService.getSensors();
    }

    /**
     * 获取磁盘相关信息
     *
     * @return {@link DiskVO DiskVO}
     */
    @GetMapping("/disk")
    public List<DiskVO> getDiskStores() {
        return osService.getDiskStores();
    }

    /**
     * 获取网络相关信息
     *
     * @return {@link NetworkVO NetworkVO}
     */
    @GetMapping("/network")
    public List<NetworkVO> getNetworkIFs() {
       return osService.getNetworkIFs();
    }

    /**
     * 获取系统CPU信息
     *
     * @return {@link CpuInfoVO CpuInfoVO}
     */
    @GetMapping("/cpu")
    public CpuInfoVO getCpuInfo() {
       return osService.getCpuInfo();
    }
}
