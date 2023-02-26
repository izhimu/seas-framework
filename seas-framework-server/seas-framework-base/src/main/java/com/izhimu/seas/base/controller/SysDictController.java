package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysDict;
import com.izhimu.seas.base.param.SysDictParam;
import com.izhimu.seas.base.service.SysDictService;
import com.izhimu.seas.base.vo.SysDictVO;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.data.controller.AbsBaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典管理控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController extends AbsBaseController<SysDictService, SysDict, SysDictVO, SysDictParam> {

    @Override
    public String logPrefix() {
        return "字典管理";
    }

    /**
     * 获取树
     *
     * @param param 查询参数 {@link SysDictParam SysDictParam}
     * @return 树数据 {@link Tree Tree}
     */
    @OperationLog("字典管理-获取树")
    @GetMapping("/tree")
    public List<Tree<Long>> tree(SysDictParam param) {
        return service.tree(param);
    }

    /**
     * 获取选择列表
     *
     * @param code String
     * @return {@link Select Select}
     */
    @OperationLog("字典管理-获取选择列表")
    @GetMapping("/select")
    public List<Select<Long>> select(String code) {
        return service.select(code);
    }
}
