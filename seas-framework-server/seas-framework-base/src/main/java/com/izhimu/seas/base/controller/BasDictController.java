package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.BasDict;
import com.izhimu.seas.base.service.BasDictService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.entity.Select;
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
@RequestMapping("/bas/dict")
public class BasDictController extends AbsBaseController<BasDictService, BasDict> {

    @Override
    public String logPrefix() {
        return "字典管理";
    }

    /**
     * 获取树
     *
     * @param param 查询参数 {@link BasDict SysDict}
     * @return 树数据 {@link Tree Tree}
     */
    @OperationLog("字典管理-获取树")
    @GetMapping("/tree")
    public List<Tree<Long>> tree(BasDict param) {
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
    public List<Select<String>> select(String code) {
        return service.select(code);
    }
}
