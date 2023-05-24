package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysMenu;
import com.izhimu.seas.base.param.SysMenuParam;
import com.izhimu.seas.base.service.SysMenuService;
import com.izhimu.seas.base.vo.SysMenuVO;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户菜单控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbsBaseController<SysMenuService, SysMenu, SysMenuVO, SysMenuParam> {

    @Override
    public String logPrefix() {
        return "用户菜单";
    }

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
     * 权限菜单
     *
     * @return 权限菜单列表 {@link SysMenuVO SysMenuVO}
     */
    @OperationLog("用户菜单-权限菜单")
    @GetMapping("/auth")
    public List<SysMenuVO> auth() {
        return service.auth();
    }
}
