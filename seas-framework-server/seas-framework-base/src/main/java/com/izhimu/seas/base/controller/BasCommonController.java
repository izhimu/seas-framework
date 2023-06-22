package com.izhimu.seas.base.controller;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.core.annotation.OperationLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用服务控制层
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@RestController
@RequestMapping("/bas/common")
public class BasCommonController {

    /**
     * 获取雪花ID
     *
     * @return 雪花ID
     */
    @OperationLog("通用服务-获取雪花ID")
    @GetMapping("/snowflake")
    public Long snowflake() {
        return IdUtil.getSnowflakeNextId();
    }
}
