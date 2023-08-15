package com.izhimu.seas.base.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.config.LogConfig;
import com.izhimu.seas.base.entity.BasAccount;
import com.izhimu.seas.base.entity.BasAccountLog;
import com.izhimu.seas.base.entity.BasDevice;
import com.izhimu.seas.base.entity.BasUser;
import com.izhimu.seas.base.mapper.BasAccountLogMapper;
import com.izhimu.seas.base.service.BasAccountLogService;
import com.izhimu.seas.base.service.BasAccountService;
import com.izhimu.seas.base.service.BasDeviceService;
import com.izhimu.seas.base.service.BasUserService;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.core.utils.IpUtil;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录日志服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAccountLogServiceImpl extends BaseServiceImpl<BasAccountLogMapper, BasAccountLog> implements BasAccountLogService {

    @Lazy
    @Resource
    private BasUserService userService;

    @Resource
    private BasAccountService accountService;

    @Resource
    private BasDeviceService deviceService;

    @Resource
    private LogConfig config;

    @Async
    @Override
    public void saveLog(Login loginDTO, int status) {
        BasAccount account = accountService.getByAccount(loginDTO.getAccount());
        if (Objects.isNull(account)) {
            return;
        }
        List<BasDevice> list = deviceService.findByCode(loginDTO.getDeviceId());
        BasDevice device = list.isEmpty() ? null : list.get(0);
        boolean newDevice = Objects.isNull(device);
        if (newDevice) {
            device = new BasDevice();
            device.setId(IdUtil.getSnowflakeNextId());
            device.setUserId(account.getUserId());
            device.setDeviceName(loginDTO.getDeviceName());
            device.setDeviceCode(loginDTO.getDeviceId());
            device.setSystemVersion(loginDTO.getSystemVersion());
        }
        BasAccountLog log = new BasAccountLog();
        log.setId(IdUtil.getSnowflakeNextId());
        log.setLoginTime(LocalDateTime.now());
        log.setLoginDeviceId(device.getId());
        log.setLoginDevice(device.getDeviceName());
        log.setLoginIp(loginDTO.getIp());
        log.setLoginAddress(IpUtil.getLocation(loginDTO.getIp()));
        log.setLoginVersion(loginDTO.getVersion());
        log.setLoginOsVersion(loginDTO.getSystemVersion());
        log.setUserId(account.getUserId());
        log.setAccountId(account.getId());
        log.setStatus(status);

        device.setLastId(log.getId());
        account.setLastId(log.getId());

        if (newDevice) {
            deviceService.save(device);
        } else {
            deviceService.updateById(device);
        }
        accountService.updateById(account);
        this.save(log);
    }

    @Override
    public Page<BasAccountLog> findPage(Page<BasAccountLog> page, BasAccountLog param) {
        Page<BasAccountLog> result = super.page(page, param);
        Set<Long> accountIds = result.getRecords().stream()
                .map(BasAccountLog::getAccountId)
                .collect(Collectors.toSet());
        Set<Long> userIds = result.getRecords().stream()
                .map(BasAccountLog::getUserId)
                .collect(Collectors.toSet());
        Map<Long, String> accountMap = accountIds.isEmpty() ? Collections.emptyMap() : accountService.findAccountMap(accountIds);
        Map<Long, String> userMap = userIds.isEmpty() ? Collections.emptyMap() : userService.findUsernameMap(userIds);
        result.getRecords().forEach(item -> {
            item.setAccount(accountMap.getOrDefault(item.getAccountId(), ""));
            item.setUserName(userMap.getOrDefault(item.getUserId(), ""));
        });
        return result;
    }

    @Override
    public BasAccountLog get(Long id) {
        BasAccountLog basAccountLog = super.getById(id);
        BasUser user = userService.getById(basAccountLog.getUserId());
        basAccountLog.setUserName(user.getUserName());
        BasAccount account = accountService.getById(basAccountLog.getAccountId());
        basAccountLog.setAccount(account.getUserAccount());
        return basAccountLog;
    }

    @Override
    public boolean cleanLog() {
        if (config.getLoginRetainTime() <= 0) {
            return true;
        }
        LocalDateTime time = LocalDateTimeUtil.offset(LocalDateTime.now(), -config.getLoginRetainTime(), ChronoUnit.DAYS);
        return this.lambdaUpdate()
                .le(BasAccountLog::getLoginTime, time)
                .remove();
    }
}
