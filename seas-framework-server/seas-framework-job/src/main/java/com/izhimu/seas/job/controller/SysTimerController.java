package com.izhimu.seas.job.controller;

import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.job.entity.SysTimer;
import com.izhimu.seas.job.param.SysTimerParam;
import com.izhimu.seas.job.service.SysTimerService;
import com.izhimu.seas.job.vo.SysTimerVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时器控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/timer")
public class SysTimerController extends AbsBaseController<SysTimerService, SysTimer, SysTimerVO, SysTimerParam> {
    @Override
    public String logPrefix() {
        return "定时任务";
    }
}
