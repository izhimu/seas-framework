package com.izhimu.seas.base.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.config.LogConfig;
import com.izhimu.seas.base.entity.BasLog;
import com.izhimu.seas.base.mapper.BasLogMapper;
import com.izhimu.seas.base.service.BasLogService;
import com.izhimu.seas.core.entity.Log;
import com.izhimu.seas.core.utils.TimeUtil;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 操作日志服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasLogServiceImpl extends BaseServiceImpl<BasLogMapper, BasLog> implements BasLogService {

    @Resource
    private LogConfig config;

    @Override
    public void saveLog(Log log) {
        if (Objects.isNull(log)) {
            return;
        }
        BasLog copy = toEntity(log);
        super.save(copy);
    }

    @Override
    public Page<BasLog> findPage(Page<BasLog> page, BasLog param) {
        QueryChainWrapper<BasLog> wrapper = super.paramQuery().param(param).orderBy().wrapper();
        wrapper.select("id", "request_url", "method", "result", "request_date", "user_name", "account", "account", "status", "runtime", "log_name");
        Page<BasLog> result = wrapper.page(page);
        result.getRecords().forEach(v -> v.setRuntimeValue(TimeUtil.millisecondsToStr(v.getRuntime())));
        return result;
    }

    @Override
    public BasLog get(Long id) {
        BasLog basLog = super.getById(id);
        basLog.setRuntimeValue(TimeUtil.millisecondsToStr(basLog.getRuntime()));
        return basLog;
    }

    @Override
    public boolean cleanLog() {
        if (config.getRetainTime() <= 0) {
            return true;
        }
        LocalDateTime time = LocalDateTimeUtil.offset(LocalDateTime.now(), -config.getRetainTime(), ChronoUnit.DAYS);
        return  this.lambdaUpdate()
                .le(BasLog::getRequestDate, time)
                .remove();
    }

    /**
     * 转换实体
     *
     * @param log Log
     * @return SysLog
     */
    private BasLog toEntity(Log log) {
        BasLog basLog = new BasLog();
        basLog.setRequestUrl(log.getRequestUrl());
        basLog.setMethod(log.getMethod());
        basLog.setParams(log.getParams());
        basLog.setResult(log.getResult());
        basLog.setRequestDate(log.getRequestDate());
        basLog.setUserId(log.getUserId());
        basLog.setAccount(log.getAccount());
        basLog.setUserName(log.getUserName());
        basLog.setLogName(log.getLogName());
        basLog.setLogType(log.getLogType());
        basLog.setIp(log.getIp());
        basLog.setStatus(log.getStatus());
        basLog.setRuntime(log.getRuntime());
        return basLog;
    }
}
