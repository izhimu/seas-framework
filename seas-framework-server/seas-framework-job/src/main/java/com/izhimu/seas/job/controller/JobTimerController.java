package com.izhimu.seas.job.controller;

import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.job.entity.JobTimer;
import com.izhimu.seas.job.service.JobTimerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时器控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/job/timer")
public class JobTimerController extends AbsBaseController<JobTimerService, JobTimer> {
    @Override
    public String logPrefix() {
        return "定时任务";
    }

    @OperationLog("@-执行")
    @PostMapping("/exec")
    public void exec(@RequestBody JobTimer entity) {
        service.execSchedule(entity);
    }
}
