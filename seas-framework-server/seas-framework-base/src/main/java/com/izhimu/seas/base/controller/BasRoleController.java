package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.*;
import com.izhimu.seas.base.service.*;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Select;
import com.izhimu.seas.data.controller.AbsBaseController;
import jakarta.annotation.Resource;
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

    @Resource
    private BasAuthMenuService authMenuService;
    @Resource
    private BasAuthOrgService authOrgService;
    @Resource
    private BasUserRoleService userRoleService;
    @Resource
    private BasAuthTopicService authTopicService;

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
    public List<Long> authMenu(@PathVariable Long id) {
        return authMenuService.findMenuIdByRoleId(id);
    }

    /**
     * 更新菜单
     *
     * @param entity {@link BasAuthMenu BasAuthMenu}
     */
    @OperationLog("@-更新菜单")
    @PostMapping("/auth/menu")
    public void updateAuthMenu(@RequestBody BasAuthMenu entity) {
        service.updateRoleMenu(entity);
    }

    /**
     * 获取菜单
     *
     * @param id id
     * @return 菜单列表
     */
    @OperationLog("@-获取组织")
    @GetMapping("/auth/org/{id}")
    public List<Long> authOrg(@PathVariable Long id) {
        return authOrgService.findOrgIdByRoleId(id);
    }

    /**
     * 更新菜单
     *
     * @param entity {@link BasAuthOrg BasAuthOrg}
     */
    @OperationLog("@-更新组织")
    @PostMapping("/auth/org")
    public void updateAuthOrg(@RequestBody BasAuthOrg entity) {
        service.updateRoleOrg(entity);
    }

    /**
     * 获取用户
     *
     * @param id id
     * @return 用户列表
     */
    @OperationLog("@-获取用户")
    @GetMapping("/user/{id}")
    public List<Long> user(@PathVariable Long id) {
        return userRoleService.findUserIdByRoleId(id);
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

    /**
     * 获取角色列表
     *
     * @return List<BasRole>
     */
    @OperationLog("@-角色列表")
    @GetMapping("/select")
    public List<Select<Long>> select() {
        return service.select();
    }

    /**
     * 获取主题
     *
     * @param id id
     * @return 主题列表
     */
    @OperationLog("@-获取主题")
    @GetMapping("/topic/{id}")
    public List<Long> topic(@PathVariable Long id) {
        return authTopicService.getTopicIdsByRoleId(id);
    }

    /**
     * 更新主题
     *
     * @param entity 主题关联 {@link BasAuthTopic BasAuthTopic}
     */
    @OperationLog("@-更新主题")
    @PostMapping("/topic")
    public void updateTopic(@RequestBody BasAuthTopic entity) {
        authTopicService.updateAuthTopic(entity);
    }
}
