package com.izhimu.seas.core.aspect;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.common.utils.JsonUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.dto.SysLogDTO;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.event.LogEvent;
import com.izhimu.seas.core.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 操作日志切面
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Around(value = "@annotation(operationLog)")
    public Object logAround(ProceedingJoinPoint pjp, OperationLog operationLog) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        Object target = pjp.getTarget();
        Object ret = pjp.proceed();
        long end = System.currentTimeMillis();
        if (!operationLog.enable()) {
            return ret;
        }
        String params;
        try {
            params = JsonUtil.toJsonStr(args);
        } catch (Exception e) {
            log.error("LogAspect Error", e);
            params = "";
        }
        try {
            SysLogDTO dto = new SysLogDTO();
            if (Objects.nonNull(request)) {
                dto.setRequestUrl(request.getRequestURI());
                dto.setMethod(request.getMethod());
                dto.setIp(ServletUtil.getClientIP(request));
            }
            if (Objects.nonNull(response)) {
                dto.setStatus(String.valueOf(response.getStatus()));
            }
            User user = SecurityUtil.contextUser();
            if (Objects.nonNull(user)) {
                dto.setUserId(user.getId());
                dto.setAccount(user.getUsername());
                dto.setUserName(user.getNickName());
            }
            Method logPrefix = ReflectUtil.getMethodByName(target.getClass(), "logPrefix");
            String logName = operationLog.value();
            if (Objects.nonNull(logPrefix)) {
                logName = ((String) logPrefix.invoke(target)).concat(logName);
            }
            dto.setLogName(logName);
            dto.setLogType(operationLog.type());
            dto.setRuntime((int) (end - start));
            dto.setRequestDate(LocalDateTime.now());
            dto.setParams(params);
            dto.setResult(JsonUtil.toJsonStr(ret));
            applicationContext.publishEvent(new LogEvent(this, dto));
        } catch (Throwable e) {
            log.error("LogAspect Error", e);
        }
        return ret;
    }
}
