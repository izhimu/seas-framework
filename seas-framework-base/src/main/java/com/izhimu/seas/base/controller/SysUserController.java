package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.dto.SysUserDTO;
import com.izhimu.seas.base.entity.SysUser;
import com.izhimu.seas.base.param.SysUserParam;
import com.izhimu.seas.base.service.SysUserService;
import com.izhimu.seas.base.vo.SysUserVO;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.mybatis.entity.Pagination;
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

    @GetMapping("/page")
    public Pagination<SysUserVO> page(Pagination<SysUser> page, SysUserParam param) {
        return service.page(page, param, SysUserVO::new);
    }

    @GetMapping("/{id}")
    public SysUserVO get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public void save(@RequestBody SysUserDTO user) {
        service.saveUser(user);
    }

    @PutMapping
    public void update(@RequestBody SysUserDTO user) {
        service.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delUser(id);
    }

    @GetMapping("/list")
    public List<SysUserVO> list() {
        return service.getUserList();
    }

    @GetMapping("/like")
    public List<Select<String>> like(String search) {
        return service.likeUser(search);
    }
}
