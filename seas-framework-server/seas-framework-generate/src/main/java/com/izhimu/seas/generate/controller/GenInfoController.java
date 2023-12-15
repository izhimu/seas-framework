package com.izhimu.seas.generate.controller;

import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.generate.entity.GenInfo;
import com.izhimu.seas.generate.service.GenInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 代码生成控制层
 *
 * @author haoran
 */
@RestController
@RequestMapping("/gen/info")
public class GenInfoController {

    @Resource
    private GenInfoService infoService;

    /**
     * 获取代码生成信息
     *
     * @param sourceId 数据源ID
     * @param table    表名
     * @return 代码生成信息
     */
    @OperationLog("代码生成-获取信息")
    @GetMapping
    public GenInfo getInfo(Long sourceId, String table) {
        return infoService.getInfo(sourceId, table);
    }

    /**
     * 生成代码
     *
     * @param info 代码生成信息
     */
    @OperationLog("代码生成-生成")
    @PostMapping("/create")
    public void create(@RequestBody GenInfo info) {
        infoService.create(info);
    }


    /**
     * 预览生成代码
     *
     * @param info 代码生成信息
     */
    @OperationLog("代码生成-预览")
    @PostMapping("/preview")
    public void preview(@RequestBody GenInfo info) {
        infoService.preview(info);
    }
}
