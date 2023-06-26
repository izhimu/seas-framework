package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.BasConf;
import com.izhimu.seas.base.service.BasConfService;
import com.izhimu.seas.data.controller.AbsBaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配置信息控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/bas/conf")
public class BasConfController extends AbsBaseController<BasConfService, BasConf> {

    @Override
    public String logPrefix() {
        return "配置信息";
    }
}
