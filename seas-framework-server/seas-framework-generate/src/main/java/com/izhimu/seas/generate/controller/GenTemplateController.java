package com.izhimu.seas.generate.controller;

import cn.hutool.core.convert.Convert;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.generate.entity.GenTemplate;
import com.izhimu.seas.generate.entity.GenTemplateAssets;
import com.izhimu.seas.generate.service.GenTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public boolean assetsSave(@RequestBody Map<String, Object> map) {
        List<GenTemplateAssets> data = Convert.toList(GenTemplateAssets.class, map.get("data"));
        return service.assetsSave(data);
    }

    @OperationLog("@-模板列表")
    @GetMapping("/list")
    public List<GenTemplate> list() {
        return service.list();
    }

    @OperationLog("@-模板列表")
    @PostMapping("/copy/{id}")
    public boolean copy(@PathVariable Long id) {
        return service.copy(id);
    }
}
