package com.izhimu.seas.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.izhimu.seas.base.entity.BasAuthMenu;
import com.izhimu.seas.base.mapper.BasAuthMenuMapper;
import com.izhimu.seas.base.service.BasAuthMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 菜单权限服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BasAuthMenuServiceImpl extends ServiceImpl<BasAuthMenuMapper, BasAuthMenu> implements BasAuthMenuService {
}
