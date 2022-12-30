package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysDict;
import com.izhimu.seas.base.param.SysDictParam;
import com.izhimu.seas.base.service.SysDictService;
import com.izhimu.seas.base.vo.SysDictVO;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.mybatis.entity.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Resource
    private SysDictService service;

    @GetMapping("/tree")
    public List<Tree<Long>> tree(SysDictParam param) {
        return service.tree(param);
    }

    @GetMapping("/page")
    public Pagination<SysDictVO> page(Pagination<SysDict> page, SysDictParam param) {
        return service.page(page, param, SysDictVO::new);
    }

    @GetMapping("/{id}")
    public SysDictVO get(@PathVariable Long id) {
        return service.get(id, SysDictVO.class);
    }

    @PostMapping
    public void save(@RequestBody SysDict role) {
        service.save(role);
    }

    @PutMapping
    public void update(@RequestBody SysDict role) {
        service.updateById(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }


    @GetMapping("/select")
    public List<Select<Long>> select(String code) {
        return service.select(code);
    }
}
