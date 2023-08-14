package com.izhimu.seas.core.aspect;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Log;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.enums.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.utils.JsonUtil;
import com.izhimu.seas.core.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

    private static final String PLACEHOLDER = "@";

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Around(value = "@annotation(operationLog)")
    public Object logAround(ProceedingJoinPoint pjp, OperationLog operationLog) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        String params;
        try {
            params = JsonUtil.toJsonStr(args);
        } catch (Exception e) {
            log.error("LogAspect Error", e);
            params = "";
        }
        Object target = pjp.getTarget();
        Object ret = pjp.proceed();
        long end = System.currentTimeMillis();
        if (!operationLog.enable()) {
            return ret;
        }
        try {
            Log data = new Log();
            if (Objects.nonNull(request)) {
                data.setRequestUrl(request.getRequestURI());
                data.setMethod(request.getMethod());
                data.setIp(ServletUtil.getClientIP(request));
            }
            if (Objects.nonNull(response)) {
                data.setStatus(String.valueOf(response.getStatus()));
            }
            User user = SecurityUtil.contextUser();
            if (Objects.nonNull(user)) {
                data.setUserId(user.getId());
                data.setAccount(user.getUsername());
                data.setUserName(user.getNickName());
            }
            Method logPrefix = ReflectUtil.getMethodByName(target.getClass(), "logPrefix");
            String logName = operationLog.value();
            if (Objects.nonNull(logPrefix) && logName.contains(PLACEHOLDER)) {
                String invoke = (String) logPrefix.invoke(target);
                logName = logName.replace(PLACEHOLDER, invoke);
            }
            data.setLogName(logName);
            data.setLogType(operationLog.type());
            data.setRuntime((int) (end - start));
            data.setRequestDate(LocalDateTime.now());
            data.setParams(params);
            data.setResult(JsonUtil.toJsonStr(ret));
            EventManager.trigger(CoreEvent.E_LOG, data);
        } catch (Throwable e) {
            log.error("LogAspect Error", e);
        }
        return ret;
    }
}
