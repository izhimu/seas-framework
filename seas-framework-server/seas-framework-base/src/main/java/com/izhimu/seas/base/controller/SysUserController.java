package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.SysUser;
import com.izhimu.seas.base.service.SysUserService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.data.entity.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService service;

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Pagination Pagination}
     * @param param 查询参数 {@link SysUser SysUser}
     * @return 分页数据 {@link SysUser SysUser}
     */
    @OperationLog("用户管理-分页查询")
    @GetMapping("/page")
    public Pagination<SysUser> page(Pagination<SysUser> page, SysUser param) {
        return service.page(page, param);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link SysUser SysUser}
     */
    @OperationLog("用户管理-详情")
    @GetMapping("/{id}")
    public SysUser get(@PathVariable Long id) {
        return service.get(id);
    }

    /**
     * 新增
     *
     * @param user {@link SysUser SysUser}
     */
    @OperationLog("用户管理-新增")
    @PostMapping
    public void save(@RequestBody SysUser user) {
        service.saveUser(user);
    }

    /**
     * 更新
     *
     * @param user {@link SysUser SysUser}
     */
    @OperationLog("用户管理-更新")
    @PutMapping
    public void update(@RequestBody SysUser user) {
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
     * @return {@link SysUser SysUser}
     */
    @OperationLog("用户管理-列表")
    @GetMapping("/list")
    public List<SysUser> list() {
        return service.getUserList();
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
}
