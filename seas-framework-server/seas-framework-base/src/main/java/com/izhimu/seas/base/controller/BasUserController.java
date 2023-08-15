package com.izhimu.seas.base.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasUser;
import com.izhimu.seas.base.service.BasUserService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Select;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/bas/user")
public class BasUserController {

    @Resource
    private BasUserService service;

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Page Page}
     * @param param 查询参数 {@link BasUser SysUser}
     * @return 分页数据 {@link BasUser SysUser}
     */
    @OperationLog("用户管理-分页查询")
    @GetMapping("/page")
    public Page<BasUser> page(Page<BasUser> page, BasUser param) {
        return service.page(page, param);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link BasUser SysUser}
     */
    @OperationLog("用户管理-详情")
    @GetMapping("/{id}")
    public BasUser get(@PathVariable Long id) {
        return service.get(id);
    }

    /**
     * 新增
     *
     * @param user {@link BasUser SysUser}
     */
    @OperationLog("用户管理-新增")
    @PostMapping
    public void save(@RequestBody BasUser user) {
        service.saveUser(user);
    }

    /**
     * 更新
     *
     * @param user {@link BasUser SysUser}
     */
    @OperationLog("用户管理-更新")
    @PutMapping
    public void update(@RequestBody BasUser user) {
        service.updateUser(user);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @OperationLog("用户管理-删除")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delUser(id);
    }

    /**
     * 列表
     *
     * @return {@link BasUser SysUser}
     */
    @OperationLog("用户管理-列表")
    @GetMapping("/list")
    public List<BasUser> list() {
        return service.findUserList();
    }

    /**
     * 模糊查询
     *
     * @param search 查询参数
     * @return {@link Select Select}
     */
    @OperationLog("用户管理-模糊查询")
    @GetMapping("/like")
    public List<Select<String>> like(String search) {
        return service.likeUser(search);
    }

    /**
     * 当前用户信息
     *
     * @return user
     */
    @OperationLog("用户管理-当前用户")
    @GetMapping("/current")
    public BasUser current() {
        return service.current();
    }
}
