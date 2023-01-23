package com.izhimu.seas.healthy.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.izhimu.seas.healthy.service.OsService;
import com.izhimu.seas.healthy.vo.*;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
import oshi.hardware.Sensors;
import oshi.software.os.FileSystem;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * OS信息服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class OsServiceImpl implements OsService {
    @Override
    public String getOs() {
        return OshiUtil.getOs().toString();
    }

    @Override
    public MemoryVO getMemory() {
        GlobalMemory memory = OshiUtil.getMemory();
        MemoryVO vo = new MemoryVO();
        vo.setTotal(memory.getTotal());
        vo.setTotalStr(FormatUtil.formatBytes(vo.getTotal()));
        vo.setUsed(memory.getTotal()-memory.getAvailable());
        vo.setUsedStr(FormatUtil.formatBytes(vo.getUsed()));
        vo.setSwapTotal(memory.getVirtualMemory().getSwapTotal());
        vo.setSwapTotalStr(FormatUtil.formatBytes(vo.getSwapTotal()));
        vo.setSwapUsed(memory.getVirtualMemory().getSwapUsed());
        vo.setSwapUsedStr(FormatUtil.formatBytes(vo.getSwapUsed()));
        vo.setVirtualMax(memory.getVirtualMemory().getVirtualMax());
        vo.setVirtualMaxStr(FormatUtil.formatBytes(vo.getVirtualMax()));
        vo.setVirtualInUse(memory.getVirtualMemory().getVirtualInUse());
        vo.setVirtualInUseStr(FormatUtil.formatBytes(vo.getVirtualInUse()));
        return vo;
    }

    @Override
    public SensorsVO getSensors() {
        Sensors sensors = OshiUtil.getSensors();
        SensorsVO vo = new SensorsVO();
        vo.setCpuTemperature(sensors.getCpuTemperature());
        vo.setCpuVoltage(sensors.getCpuVoltage());
        List<Integer> fanList = new ArrayList<>();
        for (int fanSpeed : sensors.getFanSpeeds()) {
            fanList.add(fanSpeed);
        }
        vo.setFanSpeeds(fanList);
        return vo;
    }

    @Override
    public List<DiskVO> getDiskStores() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        FileSystem fileSystem = operatingSystem.getFileSystem();
        return fileSystem.getFileStores().stream()
                .map(v -> {
                    DiskVO vo = new DiskVO();
                    vo.setUuid(v.getUUID());
                    vo.setName(v.getName());
                    vo.setVolume(v.getVolume());
                    vo.setType(v.getType());
                    vo.setTotal(v.getTotalSpace());
                    vo.setTotalStr(FormatUtil.formatBytes(vo.getTotal()));
                    vo.setUsed(v.getTotalSpace() - v.getFreeSpace());
                    vo.setUsedStr(FormatUtil.formatBytes(vo.getUsed()));
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<NetworkVO> getNetworkIFs() {
        List<NetworkIF> networkIFs = OshiUtil.getNetworkIFs();
        return networkIFs.stream()
                .map(v -> {
                    NetworkVO vo = new NetworkVO();
                    vo.setId(IdUtil.nanoId());
                    vo.setName(v.getName());
                    vo.setAlias(v.getIfAlias());
                    vo.setDisplayName(v.getDisplayName());
                    vo.setStatus(v.getIfOperStatus().name());
                    vo.setMac(v.getMacaddr());
                    vo.setIpv4(v.getIPv4addr().length > 0 ? v.getIPv4addr()[0] : "");
                    vo.setIpv6(v.getIPv6addr().length > 0 ? v.getIPv6addr()[0] : "");
                    vo.setMtu(v.getMTU());
                    vo.setSpeed((int) (v.getSpeed() / 1000000));
                    vo.setBytesRecv(FormatUtil.formatBytes(v.getBytesRecv()));
                    vo.setBytesSent(FormatUtil.formatBytes(v.getBytesSent()));
                    vo.setPacketsRecv(FormatUtil.formatBytes(v.getPacketsRecv()));
                    vo.setPacketsSent(FormatUtil.formatBytes(v.getPacketsSent()));
                    return vo;
                })
                .sorted(Comparator.comparing(NetworkVO::getStatus).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public CpuInfoVO getCpuInfo() {
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        CpuInfoVO vo = new CpuInfoVO();
        vo.setTotal(cpuInfo.getCpuNum());
        vo.setSys(cpuInfo.getSys());
        vo.setUser(cpuInfo.getUser());
        vo.setFree(cpuInfo.getFree());
        vo.setModel(cpuInfo.getCpuModel());
        return vo;
    }
}
