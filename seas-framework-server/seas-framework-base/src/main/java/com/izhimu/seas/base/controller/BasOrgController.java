package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.BasOrg;
import com.izhimu.seas.base.service.BasOrgService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 组织架构控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/bas/org")
public class BasOrgController extends AbsBaseController<BasOrgService, BasOrg> {

    @Override
    public String logPrefix() {
        return "组织架构";
    }

    /**
     * 获取树
     *
     * @param param 查询参数
     * @return 树数据 {@link Tree Tree}
     */
    @OperationLog("@-获取树")
    @GetMapping("/tree")
    public List<Tree<Long>> tree(BasOrg param) {
        return service.tree(param);
    }

}
