package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.SysLog;
import com.izhimu.seas.base.mapper.SysLogMapper;
import com.izhimu.seas.base.service.SysLogService;
import com.izhimu.seas.common.utils.TimeUtil;
import com.izhimu.seas.core.web.entity.Log;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 操作日志服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Override
    public void saveLog(Log log) {
        if (Objects.isNull(log)) {
            return;
        }
        SysLog copy = toEntity(log);
        super.save(copy);
    }

    @Override
    public Page<SysLog> findPage(Page<SysLog> page, SysLog param) {
        QueryChainWrapper<SysLog> wrapper = super.paramQuery().param(param).orderBy().wrapper();
        wrapper.select("id", "request_url", "method", "result", "request_date", "user_name", "account", "account", "status", "runtime", "log_name");
        Page<SysLog> result = wrapper.page(page);
        result.getRecords().forEach(v -> v.setRuntimeValue(TimeUtil.millisecondsToStr(v.getRuntime())));
        return result;
    }

    @Override
    public SysLog get(Long id) {
        SysLog sysLog = super.getById(id);
        sysLog.setRuntimeValue(TimeUtil.millisecondsToStr(sysLog.getRuntime()));
        return sysLog;
    }

    /**
     * 转换实体
     *
     * @param log Log
     * @return SysLog
     */
    private SysLog toEntity(Log log) {
        SysLog sysLog = new SysLog();
        sysLog.setRequestUrl(log.getRequestUrl());
        sysLog.setMethod(log.getMethod());
        sysLog.setParams(log.getParams());
        sysLog.setResult(log.getResult());
        sysLog.setRequestDate(log.getRequestDate());
        sysLog.setUserId(log.getUserId());
        sysLog.setAccount(log.getAccount());
        sysLog.setUserName(log.getUserName());
        sysLog.setLogName(log.getLogName());
        sysLog.setLogType(log.getLogType());
        sysLog.setIp(log.getIp());
        sysLog.setStatus(log.getStatus());
        sysLog.setRuntime(log.getRuntime());
        return sysLog;
    }
}
