package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysMenu;
import com.izhimu.seas.base.param.SysMenuParam;
import com.izhimu.seas.base.service.SysMenuService;
import com.izhimu.seas.base.vo.SysMenuVO;
import com.izhimu.seas.mybatis.entity.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户菜单控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Resource
    private SysMenuService service;

    @GetMapping("/tree")
    public List<Tree<Long>> tree(SysMenuParam param) {
        return service.tree(param);
    }

    @GetMapping("/page")
    public Pagination<SysMenuVO> page(Pagination<SysMenu> page, SysMenuParam param) {
        return service.page(page, param, SysMenuVO::new);
    }

    @GetMapping("/auth")
    public List<SysMenuVO> auth() {
        return service.auth();
    }

    @GetMapping("/{id}")
    public SysMenuVO get(@PathVariable Long id) {
        return service.get(id, SysMenuVO.class);
    }

    @PostMapping
    public void save(@RequestBody SysMenu menu) {
        service.save(menu);
    }

    @PutMapping
    public void update(@RequestBody SysMenu menu) {
        service.updateById(menu);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }
}
