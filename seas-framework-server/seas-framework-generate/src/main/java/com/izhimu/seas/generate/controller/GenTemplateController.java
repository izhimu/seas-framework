package com.izhimu.seas.generate.controller;

import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.generate.entity.GenTemplate;
import com.izhimu.seas.generate.service.GenTemplateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
