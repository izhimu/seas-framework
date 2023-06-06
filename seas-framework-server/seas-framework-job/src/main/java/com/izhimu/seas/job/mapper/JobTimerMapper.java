package com.izhimu.seas.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izhimu.seas.job.entity.JobTimer;
import org.springframework.stereotype.Repository;

/**
 * 定时器映射层
 *
 * @author haoran
 * @version v1.0
 */
@Repository
public interface JobTimerMapper extends BaseMapper<JobTimer> {
}
