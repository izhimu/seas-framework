package com.izhimu.seas.base.controller;

import com.izhimu.seas.base.entity.BasTopicMenu;
import com.izhimu.seas.base.service.BasTopicMenuService;
import com.izhimu.seas.core.annotation.OperationLog;
import com.izhimu.seas.data.controller.AbsBaseController;
import com.izhimu.seas.base.entity.BasTopic;
import com.izhimu.seas.base.service.BasTopicService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 主题表控制层
 *
 * @author haoran
 * @version v1.0
 */
@RestController
@RequestMapping("/bas/topic")
public class BasTopicController extends AbsBaseController<BasTopicService, BasTopic> {

    @Resource
    private BasTopicMenuService topicMenuService;

    @Override
    public String logPrefix() {
        return "主题管理";
    }

    /**
     * 获取菜单
     *
     * @param id id
     * @return 菜单列表
     */
    @OperationLog("@-获取菜单")
    @GetMapping("/menu/{id}")
    public List<Long> topicMenu(@PathVariable Long id) {
        return topicMenuService.findMenuIdsByTopicId(id);
    }

    /**
     * 更新菜单
     *
     * @param entity 菜单实体 {@link BasTopicMenu BasTopicMenu}
     */
    @OperationLog("@-更新菜单")
    @PostMapping("/menu")
    public void updateTopicMenu(@RequestBody BasTopicMenu entity) {
        service.updateTopicMenu(entity);
    }

    /**
     * 获取主题列表
     *
     * @return 主题列表 {@link BasTopic BasTopic}
     */
    @OperationLog("@-获取主题列表")
    @GetMapping("/list")
    public List<BasTopic> list() {
        return service.list();
    }

    /**
     * 获取授权主题列表
     *
     * @return 授权主题列表 {@link BasTopic BasTopic}
     */
    @OperationLog("@-获取授权主题列表")
    @GetMapping("/auth/list")
    public List<BasTopic> authList() {
        return service.authTopicList();
    }
}
