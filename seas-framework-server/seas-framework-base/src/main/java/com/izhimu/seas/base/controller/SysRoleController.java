package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.dto.SysAuthMenuDTO;
import com.izhimu.seas.base.dto.SysUserRoleDTO;
import com.izhimu.seas.base.entity.SysRole;
import com.izhimu.seas.base.param.SysRoleParam;
import com.izhimu.seas.base.service.SysRoleService;
import com.izhimu.seas.base.vo.SysRoleVO;
import com.izhimu.seas.core.annotation.OperationLog;
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
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService service;

    /**
     * 分页查询
     *
     * @param page  分页参数 {@link Pagination Pagination}
     * @param param 查询参数 {@link SysRoleParam SysRoleParam}
     * @return 分页数据 {@link SysRoleVO SysRoleVO}
     */
    @OperationLog("用户角色-分页查询")
    @GetMapping("/page")
    public Pagination<SysRoleVO> page(Pagination<SysRole> page, SysRoleParam param) {
        return service.page(page, param, SysRoleVO::new);
    }

    /**
     * 详情
     *
     * @param id id
     * @return {@link SysRoleVO SysRoleVO}
     */
    @OperationLog("用户角色-详情")
    @GetMapping("/{id}")
    public SysRoleVO get(@PathVariable Long id) {
        return service.get(id, SysRoleVO.class);
    }

    /**
     * 保存
     *
     * @param role {@link SysRole SysRole}
     */
    @OperationLog("用户角色-保存")
    @PostMapping
    public void save(@RequestBody SysRole role) {
        service.save(role);
    }

    /**
     * 更新
     *
     * @param role {@link SysRole SysRole}
     */
    @OperationLog("用户角色-更新")
    @PutMapping
    public void update(@RequestBody SysRole role) {
        service.updateById(role);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @OperationLog("用户角色-删除")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
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
     * @param dto {@link SysAuthMenuDTO SysAuthMenuDTO}
     */
    @OperationLog("用户角色-更新菜单")
    @PostMapping("/auth/menu")
    public void updateAuthMenu(@RequestBody SysAuthMenuDTO dto) {
        service.updateRoleMenu(dto);
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
     * @param dto {@link SysUserRoleDTO SysUserRoleDTO}
     */
    @OperationLog("用户角色-更新用户")
    @PostMapping("/user")
    public void updateUser(@RequestBody SysUserRoleDTO dto) {
        service.updateUserRole(dto);
    }
}
