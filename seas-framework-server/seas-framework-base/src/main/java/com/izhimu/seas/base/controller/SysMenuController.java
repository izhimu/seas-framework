package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysMenu;
import com.izhimu.seas.base.param.SysMenuParam;
import com.izhimu.seas.base.service.SysMenuService;
import com.izhimu.seas.base.vo.SysMenuVO;
import com.izhimu.seas.core.annotation.OperationLog;
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

    /**
     * 获取树
     *
     * @param param 查询参数 {@link SysMenuParam SysMenuParam}
     * @return {@link Tree Tree}
     */
    @OperationLog("用户菜单-获取树")
    @GetMapping("/tree")
    public List<Tree<Long>> tree(SysMenuParam param) {
        return service.tree(param);
    }

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Pagination Pagination}
     * @param param 查询参数 {@link SysMenuParam SysMenuParam}
     * @return 分页数据 {@link SysMenuVO SysMenuVO}
     */
    @OperationLog("用户菜单-分页查询")
    @GetMapping("/page")
    public Pagination<SysMenuVO> page(Pagination<SysMenu> page, SysMenuParam param) {
        return service.page(page, param, SysMenuVO::new);
    }

    /**
     * 权限菜单
     *
     * @return 权限菜单列表 {@link SysMenuVO SysMenuVO}
     */
    @OperationLog("用户菜单-权限菜单")
    @GetMapping("/auth")
    public List<SysMenuVO> auth() {
        return service.auth();
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link SysMenuVO SysMenuVO}
     */
    @OperationLog("用户菜单-详情")
    @GetMapping("/{id}")
    public SysMenuVO get(@PathVariable Long id) {
        return service.get(id, SysMenuVO.class);
    }

    /**
     * 保存
     *
     * @param menu {@link SysMenu SysMenu}
     */
    @OperationLog("用户菜单-保存")
    @PostMapping
    public void save(@RequestBody SysMenu menu) {
        service.save(menu);
    }

    /**
     * 更新
     *
     * @param menu {@link SysMenu SysMenu}
     */
    @OperationLog("用户菜单-更新")
    @PutMapping
    public void update(@RequestBody SysMenu menu) {
        service.updateById(menu);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @OperationLog("用户菜单-删除")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }
}
