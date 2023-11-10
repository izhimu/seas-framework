package com.izhimu.seas.generate.controller;

import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.generate.entity.GenTemplateAssets;
import com.izhimu.seas.generate.service.GenTemplateAssetsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模板资源控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/gen/template/assets")
public class GenTemplateAssetsController extends AbsBaseController<GenTemplateAssetsService, GenTemplateAssets> {

    @Override
    public String logPrefix() {
        return "模板资源管理";
    }
}
