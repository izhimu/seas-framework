package com.izhimu.seas.base.controller;

import cn.hutool.core.lang.tree.Tree;
import com.izhimu.seas.base.entity.SysDict;
import com.izhimu.seas.base.param.SysDictParam;
import com.izhimu.seas.base.service.SysDictService;
import com.izhimu.seas.base.vo.SysDictVO;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.core.web.entity.Select;
import com.izhimu.seas.mybatis.entity.Pagination;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典管理控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {

    @Resource
    private SysDictService service;

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
     * 分页查询
     *
     * @param page  分页参数 {@link Pagination Pagination}
     * @param param 查询参数 {@link SysDictParam SysDictParam}
     * @return 分页数据 {@link SysDictVO SysDictVO}
     */
    @OperationLog("字典管理-分页查询")
    @GetMapping("/page")
    public Pagination<SysDictVO> page(Pagination<SysDict> page, SysDictParam param) {
        return service.page(page, param, SysDictVO::new);
    }

    /**
     * 详情
     *
     * @param id id
     * @return 数据 {@link SysDictVO SysDictVO}
     */
    @OperationLog("字典管理-详情")
    @GetMapping("/{id}")
    public SysDictVO get(@PathVariable Long id) {
        return service.get(id, SysDictVO.class);
    }

    /**
     * 保存
     *
     * @param role {@link SysDict SysDict}
     */
    @OperationLog("字典管理-保存")
    @PostMapping
    public void save(@RequestBody SysDict role) {
        service.save(role);
    }

    /**
     * 更新
     *
     * @param role {@link SysDict SysDict}
     */
    @OperationLog("字典管理-更新")
    @PutMapping
    public void update(@RequestBody SysDict role) {
        service.updateById(role);
    }

    /**
     * 删除
     *
     * @param id id
     */
    @OperationLog("字典管理-删除")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.removeById(id);
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
