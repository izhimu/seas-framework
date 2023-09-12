package com.izhimu.seas.job.handler;

import com.izhimu.seas.core.annotation.JobRegister;
import com.izhimu.seas.core.scan.IScanHandler;
import com.izhimu.seas.job.entity.JobTimer;
import com.izhimu.seas.job.service.JobTimerService;
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

    private final JobTimerService timerService;

    public JobRegisterScanHandler(JobTimerService timerService) {
        this.timerService = timerService;
    }

    @Override
    public String name() {
        return "JobRegister";
    }

    @Override
    public void scan(Reflections reflections) {
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(JobRegister.class);
        for (Class<?> aClass : typesAnnotatedWith) {
            try {
                JobRegister annotation = aClass.getAnnotation(JobRegister.class);
                List<JobTimer> list = timerService.lambdaQuery()
                        .eq(JobTimer::getKey, annotation.key())
                        .list();
                if (!list.isEmpty()) {
                    continue;
                }
                JobTimer timer = new JobTimer();
                timer.setStatus(annotation.enable() ? 1 : 0);
                timer.setName(annotation.name());
                timer.setKey(annotation.key());
                timer.setType(annotation.type().getType());
                timer.setExpression(annotation.expression());
                timer.setClassPath(aClass.getName());
                timerService.add(timer);
            } catch (Exception e) {
                log.error("#ScanServer :JobRegister: Error ", e);
            }
        }
    }
}
