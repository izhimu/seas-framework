package com.izhimu.seas.job.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.job.entity.SysTimer;
import com.izhimu.seas.job.mapper.SysTimerMapper;
import com.izhimu.seas.job.service.SysTimerService;
import org.springframework.stereotype.Service;

/**
 * 定时器服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
public class SysTimerServiceImpl extends BaseServiceImpl<SysTimerMapper, SysTimer> implements SysTimerService {
}
