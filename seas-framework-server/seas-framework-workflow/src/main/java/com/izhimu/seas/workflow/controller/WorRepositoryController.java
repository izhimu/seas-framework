package com.izhimu.seas.workflow.controller;

import com.izhimu.seas.workflow.service.WorRepositoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程资源控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/wor/repository")
public class WorRepositoryController {

    @Resource
    private WorRepositoryService service;

    @GetMapping("/test")
    public void test(){
//        System.out.println(service.getNativeService());
    }
}
