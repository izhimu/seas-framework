package com.izhimu.seas.core.aspect;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ReflectUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Log;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.event.CoreEvent;
import com.izhimu.seas.core.event.EventManager;
import com.izhimu.seas.core.utils.IpUtil;
import com.izhimu.seas.core.utils.JsonUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.izhimu.seas.core.log.LogHelper.log;

/**
 * 操作日志切面
 *
 * @author haoran
 * @version v1.0
 */
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
        if (!operationLog.enable()) {
            return pjp.proceed();
        }
        long start = System.currentTimeMillis();
        Object[] args = pjp.getArgs();
        String params;
        try {
            params = "[".concat(Arrays.stream(args).filter(Serializable.class::isInstance).map(JsonUtil::toJsonStr).collect(Collectors.joining(","))).concat("]");
        } catch (Exception e) {
            log.warn("OperationLog param to json str error: {}", e.getMessage());
            params = "";
        }
        Object target = pjp.getTarget();
        Object ret = pjp.proceed();
        long end = System.currentTimeMillis();
        try {
            Log data = new Log();
            if (Objects.nonNull(request)) {
                data.setRequestUrl(request.getRequestURI());
                data.setMethod(request.getMethod());
                data.setIp(IpUtil.getClientIP(request));
            }
            if (Objects.nonNull(response)) {
                data.setStatus(String.valueOf(response.getStatus()));
            }
            User user = getUser();
            if (Objects.nonNull(user)) {
                data.setUserId(user.getId());
                data.setAccount(user.getUserAccount());
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
        } catch (Exception e) {
            log.error(e);
        }
        return ret;
    }

    private User getUser() {
        User user = null;
        try {
            user = StpUtil.getSession().getModel(SaSession.USER, User.class);
        } catch (Exception e) {
            log.warn("OperationLog get user error: {}", e.getMessage());
        }
        return user;
    }
}
