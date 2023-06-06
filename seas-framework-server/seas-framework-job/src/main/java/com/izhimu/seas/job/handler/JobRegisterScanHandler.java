package com.izhimu.seas.job.handler;

import com.izhimu.seas.core.scan.IScanHandler;
import com.izhimu.seas.core.annotation.JobRegister;
import com.izhimu.seas.job.entity.SysTimer;
import com.izhimu.seas.job.service.SysTimerService;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;

/**
 * @author haoran
 * @version v1.0
 */
@Slf4j
public class JobRegisterScanHandler implements IScanHandler {

    private final SysTimerService timerService;

    public JobRegisterScanHandler(SysTimerService timerService) {
        this.timerService = timerService;
    }

    @Override
    public String name() {
        return "任务注册扫描处理";
    }

    @Override
    public void scan(Reflections reflections) {
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(JobRegister.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            try {
                JobRegister annotation = aClass.getAnnotation(JobRegister.class);
                List<SysTimer> list = timerService.lambdaQuery()
                        .eq(SysTimer::getKey, annotation.key())
                        .list();
                boolean save = list.isEmpty();
                SysTimer timer = save ? new SysTimer() : list.get(0);
                timer.setStatus(annotation.enable() ? 1 : 0);
                if (save) {
                    timer.setName(annotation.name());
                    timer.setKey(annotation.key());
                    timer.setType(annotation.type().getType());
                    timer.setExpression(annotation.expression());
                    timer.setClassPath(aClass.getName());
                    timerService.add(timer);
                } else {
                    timerService.updateById(timer);
                }
            } catch (Exception e) {
                log.error("EventListener Scan Error", e);
            }
        }
    }
}
