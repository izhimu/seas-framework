package com.izhimu.seas.generate.controller;

import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.generate.db.exception.DbEngineException;
import com.izhimu.seas.generate.entity.GenDatasource;
import com.izhimu.seas.generate.service.GenDatasourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PostMapping("/test/{id}")
    public boolean test(@PathVariable Long id) throws DbEngineException {
        return service.test(id);
    }

    @OperationLog(value = "@-数据源列表")
    @GetMapping("/list")
    public List<GenDatasource> list() {
        return service.list();
    }

    @OperationLog(value = "@-表列表")
    @GetMapping("/tables/{id}")
    public List<String> tables(@PathVariable Long id, String like) {
        return service.tables(id, like);
    }
}
