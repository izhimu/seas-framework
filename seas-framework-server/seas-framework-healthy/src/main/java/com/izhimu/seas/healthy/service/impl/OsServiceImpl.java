package com.izhimu.seas.healthy.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.system.oshi.OshiUtil;
import com.izhimu.seas.healthy.entity.*;
import com.izhimu.seas.healthy.service.OsService;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.NetworkIF;
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
    public Memory getMemory() {
        GlobalMemory memory = OshiUtil.getMemory();
        Memory data = new Memory();
        data.setTotal(memory.getTotal());
        data.setTotalStr(FormatUtil.formatBytes(data.getTotal()));
        data.setUsed(memory.getTotal()-memory.getAvailable());
        data.setUsedStr(FormatUtil.formatBytes(data.getUsed()));
        data.setSwapTotal(memory.getVirtualMemory().getSwapTotal());
        data.setSwapTotalStr(FormatUtil.formatBytes(data.getSwapTotal()));
        data.setSwapUsed(memory.getVirtualMemory().getSwapUsed());
        data.setSwapUsedStr(FormatUtil.formatBytes(data.getSwapUsed()));
        data.setVirtualMax(memory.getVirtualMemory().getVirtualMax());
        data.setVirtualMaxStr(FormatUtil.formatBytes(data.getVirtualMax()));
        data.setVirtualInUse(memory.getVirtualMemory().getVirtualInUse());
        data.setVirtualInUseStr(FormatUtil.formatBytes(data.getVirtualInUse()));
        return data;
    }

    @Override
    public Sensors getSensors() {
        oshi.hardware.Sensors sensors = OshiUtil.getSensors();
        Sensors data = new Sensors();
        data.setCpuTemperature(sensors.getCpuTemperature());
        data.setCpuVoltage(sensors.getCpuVoltage());
        List<Integer> fanList = new ArrayList<>();
        for (int fanSpeed : sensors.getFanSpeeds()) {
            fanList.add(fanSpeed);
        }
        data.setFanSpeeds(fanList);
        return data;
    }

    @Override
    public List<Disk> getDiskStores() {
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        FileSystem fileSystem = operatingSystem.getFileSystem();
        return fileSystem.getFileStores().stream()
                .map(v -> {
                    Disk data = new Disk();
                    data.setUuid(v.getUUID());
                    data.setName(v.getName());
                    data.setVolume(v.getVolume());
                    data.setType(v.getType());
                    data.setTotal(v.getTotalSpace());
                    data.setTotalStr(FormatUtil.formatBytes(data.getTotal()));
                    data.setUsed(v.getTotalSpace() - v.getFreeSpace());
                    data.setUsedStr(FormatUtil.formatBytes(data.getUsed()));
                    return data;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Network> getNetworkIFs() {
        List<NetworkIF> networkIFs = OshiUtil.getNetworkIFs();
        return networkIFs.stream()
                .map(v -> {
                    Network data = new Network();
                    data.setId(IdUtil.nanoId());
                    data.setName(v.getName());
                    data.setAlias(v.getIfAlias());
                    data.setDisplayName(v.getDisplayName());
                    data.setStatus(v.getIfOperStatus().name());
                    data.setMac(v.getMacaddr());
                    data.setIpv4(v.getIPv4addr().length > 0 ? v.getIPv4addr()[0] : "");
                    data.setIpv6(v.getIPv6addr().length > 0 ? v.getIPv6addr()[0] : "");
                    data.setMtu(v.getMTU());
                    data.setSpeed((int) (v.getSpeed() / 1000000));
                    data.setBytesRecv(FormatUtil.formatBytes(v.getBytesRecv()));
                    data.setBytesSent(FormatUtil.formatBytes(v.getBytesSent()));
                    data.setPacketsRecv(FormatUtil.formatBytes(v.getPacketsRecv()));
                    data.setPacketsSent(FormatUtil.formatBytes(v.getPacketsSent()));
                    return data;
                })
                .sorted(Comparator.comparing(Network::getStatus).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public CpuInfo getCpuInfo() {
        cn.hutool.system.oshi.CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        CpuInfo data = new CpuInfo();
        data.setTotal(cpuInfo.getCpuNum());
        data.setSys(cpuInfo.getSys());
        data.setUser(cpuInfo.getUser());
        data.setFree(cpuInfo.getFree());
        data.setModel(cpuInfo.getCpuModel());
        return data;
    }
}
