package com.izhimu.seas.generate.controller;

import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.generate.entity.GenTemplate;
import com.izhimu.seas.generate.entity.GenTemplateAssets;
import com.izhimu.seas.generate.service.GenTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模板控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/gen/template")
public class GenTemplateController extends AbsBaseController<GenTemplateService, GenTemplate> {

    @Override
    public String logPrefix() {
        return "模板管理";
    }

    @OperationLog("@-获取模板资源")
    @GetMapping("/assets/{id}")
    public List<GenTemplateAssets> assets(@PathVariable Long id) {
        return service.assetsList(id);
    }

    @OperationLog("@-更新模板资源")
    @PostMapping("/assets")
    public boolean assetsSave(@RequestBody List<GenTemplateAssets> assetsList) {
        return service.assetsSave(assetsList);
    }
}
