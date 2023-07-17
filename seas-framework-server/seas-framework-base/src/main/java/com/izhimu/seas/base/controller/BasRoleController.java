package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.BasAuthMenu;
import com.izhimu.seas.base.entity.BasRole;
import com.izhimu.seas.base.entity.BasUserRole;
import com.izhimu.seas.base.service.BasRoleService;
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
@RequestMapping("/bas/role")
public class BasRoleController extends AbsBaseController<BasRoleService, BasRole> {

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
    @OperationLog("@-获取菜单")
    @GetMapping("/auth/menu/{id}")
    public List<String> authMenu(@PathVariable Long id) {
        return service.getRoleMenu(id);
    }

    /**
     * 更新菜单
     *
     * @param entity {@link BasAuthMenu SysAuthMenu}
     */
    @OperationLog("@-更新菜单")
    @PostMapping("/auth/menu")
    public void updateAuthMenu(@RequestBody BasAuthMenu entity) {
        service.updateRoleMenu(entity);
    }

    /**
     * 获取用户
     *
     * @param id id
     * @return 用户列表
     */
    @OperationLog("@-获取用户")
    @GetMapping("/user/{id}")
    public List<String> user(@PathVariable Long id) {
        return service.getUserRole(id);
    }

    /**
     * 更新用户
     *
     * @param entity {@link BasUserRole SysUserRole}
     */
    @OperationLog("@-更新用户")
    @PostMapping("/user")
    public void updateUser(@RequestBody BasUserRole entity) {
        service.updateUserRole(entity);
    }
}
