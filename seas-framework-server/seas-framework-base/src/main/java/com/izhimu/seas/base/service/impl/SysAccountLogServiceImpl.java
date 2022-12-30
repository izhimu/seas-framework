package com.izhimu.seas.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.base.entity.SysAccount;
import com.izhimu.seas.base.entity.SysAccountLog;
import com.izhimu.seas.base.entity.SysDevice;
import com.izhimu.seas.base.entity.SysUser;
import com.izhimu.seas.base.mapper.SysAccountLogMapper;
import com.izhimu.seas.base.param.SysAccountLogParam;
import com.izhimu.seas.base.service.SysAccountLogService;
import com.izhimu.seas.base.service.SysAccountService;
import com.izhimu.seas.base.service.SysDeviceService;
import com.izhimu.seas.base.service.SysUserService;
import com.izhimu.seas.base.utils.IpUtil;
import com.izhimu.seas.base.vo.SysAccountLogVO;
import com.izhimu.seas.security.dto.LoginDTO;
import com.izhimu.seas.mybatis.entity.BaseEntity;
import com.izhimu.seas.mybatis.entity.Pagination;
import com.izhimu.seas.mybatis.service.impl.BaseServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录日志服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class SysAccountLogServiceImpl extends BaseServiceImpl<SysAccountLogMapper, SysAccountLog> implements SysAccountLogService {

    @Lazy
    @Resource
    private SysUserService userService;

    @Resource
    private SysAccountService accountService;

    @Resource
    private SysDeviceService deviceService;

    @Async
    @Override
    public void saveLog(LoginDTO loginDTO, int status) {
        SysAccount account = accountService.lambdaQuery()
                .eq(SysAccount::getUserAccount, loginDTO.getAccount())
                .one();
        if (Objects.isNull(account)) {
            return;
        }
        SysDevice device = deviceService.lambdaQuery()
                .eq(SysDevice::getDeviceCode, loginDTO.getDeviceId())
                .one();
        boolean newDevice = Objects.isNull(device);
        if (newDevice) {
            device = new SysDevice();
            device.setId(IdUtil.getSnowflakeNextId());
            device.setUserId(account.getUserId());
            device.setDeviceName(loginDTO.getDeviceName());
            device.setDeviceCode(loginDTO.getDeviceId());
            device.setSystemVersion(loginDTO.getSystemVersion());
        }
        SysAccountLog log = new SysAccountLog();
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
    public Pagination<SysAccountLogVO> findPage(Pagination<SysAccountLog> page, SysAccountLogParam param) {
        Pagination<SysAccountLogVO> result = super.page(page, param, SysAccountLogVO::new);
        Set<Long> accountIds = result.getRecords().stream()
                .map(SysAccountLogVO::getAccountId)
                .collect(Collectors.toSet());
        Set<Long> userIds = result.getRecords().stream()
                .map(SysAccountLogVO::getUserId)
                .collect(Collectors.toSet());
        Map<Long, String> accountMap;
        Map<Long, String> userMap;
        if (accountIds.isEmpty()) {
            accountMap = Collections.emptyMap();
        } else {
            accountMap = accountService.lambdaQuery()
                    .select(SysAccount::getId, SysAccount::getUserAccount)
                    .in(SysAccount::getId, accountIds)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(BaseEntity::getId, SysAccount::getUserAccount));
        }
        if (userIds.isEmpty()) {
            userMap = Collections.emptyMap();
        } else {
            userMap = userService.lambdaQuery()
                    .select(SysUser::getId, SysUser::getUserName)
                    .in(SysUser::getId, userIds)
                    .list()
                    .stream()
                    .collect(Collectors.toMap(BaseEntity::getId, SysUser::getUserName));
        }
        result.getRecords().forEach(item -> {
            item.setAccount(accountMap.getOrDefault(item.getAccountId(), ""));
            item.setUserName(userMap.getOrDefault(item.getUserId(), ""));
        });
        return result;
    }

    @Override
    public SysAccountLogVO get(Long id) {
        SysAccountLogVO sysAccountLogVO = super.get(id, SysAccountLogVO.class);
        SysUser user = userService.getById(sysAccountLogVO.getUserId());
        sysAccountLogVO.setUserName(user.getUserName());
        SysAccount account = accountService.getById(sysAccountLogVO.getAccountId());
        sysAccountLogVO.setAccount(account.getUserAccount());
        return sysAccountLogVO;
    }
}
