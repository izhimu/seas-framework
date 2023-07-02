package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.BasConf;
import com.izhimu.seas.base.service.BasConfService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import org.springframework.web.bind.annotation.GetMapping;
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

    @OperationLog("-获取配置")
    @GetMapping("/get")
    public String get(String key) {
        return service.get(key);
    }

    @OperationLog("-Key是否可用")
    @GetMapping("/usable")
    public boolean usable(Long id, String key) {
        return service.usableKey(id, key);
    }
}
