package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.SysAuthMenu;
import com.izhimu.seas.base.entity.SysRole;
import com.izhimu.seas.base.entity.SysUserRole;
import com.izhimu.seas.base.service.SysRoleService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户角色控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbsBaseController<SysRoleService, SysRole> {

    @Override
    public String logPrefix() {
        return "用户角色";
    }

    /**
     * 获取菜单
     *
     * @param id id
     * @return 菜单列表
     */
    @OperationLog("用户角色-获取菜单")
    @GetMapping("/auth/menu/{id}")
    public List<String> authMenu(@PathVariable Long id) {
        return service.getRoleMenu(id);
    }

    /**
     * 更新菜单
     *
     * @param entity {@link SysAuthMenu SysAuthMenu}
     */
    @OperationLog("用户角色-更新菜单")
    @PostMapping("/auth/menu")
    public void updateAuthMenu(@RequestBody SysAuthMenu entity) {
        service.updateRoleMenu(entity);
    }

    /**
     * 获取用户
     *
     * @param id id
     * @return 用户列表
     */
    @OperationLog("用户角色-获取用户")
    @GetMapping("/user/{id}")
    public List<String> user(@PathVariable Long id) {
        return service.getUserRole(id);
    }

    /**
     * 更新用户
     *
     * @param entity {@link SysUserRole SysUserRole}
     */
    @OperationLog("用户角色-更新用户")
    @PostMapping("/user")
    public void updateUser(@RequestBody SysUserRole entity) {
        service.updateUserRole(entity);
    }
}
