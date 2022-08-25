package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.dto.SysAuthMenuDTO;
import com.izhimu.seas.base.dto.SysUserRoleDTO;
import com.izhimu.seas.base.entity.SysRole;
import com.izhimu.seas.base.param.SysRoleParam;
import com.izhimu.seas.base.service.SysRoleService;
import com.izhimu.seas.base.vo.SysRoleVO;
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

    @GetMapping("/page")
    public Pagination<SysRoleVO> page(Pagination<SysRole> page, SysRoleParam param) {
        return service.page(page, param, SysRoleVO::new);
    }

    @GetMapping("/{id}")
    public SysRoleVO get(@PathVariable Long id) {
        return service.get(id, SysRoleVO.class);
    }

    @PostMapping
    public void save(@RequestBody SysRole role) {
        service.save(role);
    }

    @PutMapping
    public void update(@RequestBody SysRole role) {
        service.updateById(role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
    }

    @GetMapping("/auth/menu/{id}")
    public List<String> authMenu(@PathVariable Long id) {
        return service.getRoleMenu(id);
    }

    @PostMapping("/auth/menu")
    public void updateAuthMenu(@RequestBody SysAuthMenuDTO dto) {
        service.updateRoleMenu(dto);
    }

    @GetMapping("/user/{id}")
    public List<String> user(@PathVariable Long id) {
        return service.getUserRole(id);
    }

    @PostMapping("/user")
    public void updateUser(@RequestBody SysUserRoleDTO dto) {
        service.updateUserRole(dto);
    }
}
