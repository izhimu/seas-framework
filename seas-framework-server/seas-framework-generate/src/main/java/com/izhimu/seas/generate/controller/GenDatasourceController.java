package com.izhimu.seas.generate.controller;

import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.generate.entity.GenDatasource;
import com.izhimu.seas.generate.service.GenDatasourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据源控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/gen/datasource")
public class GenDatasourceController extends AbsBaseController<GenDatasourceService, GenDatasource> {

    @Override
    public String logPrefix() {
        return "数据源管理";
    }

    @OperationLog(value = "@-连接测试")
    @GetMapping("/test/{id}")
    public boolean test(@PathVariable Long id) {
        return service.test(id);
    }
}
