package com.izhimu.seas.cache.helper;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.cache.service.RedisService;

/**
 * RedisHelper
 *
 * @author haoran
 * @version v1.0
 */
public class RedisHelper {

    private static RedisHelper helper;

    private final RedisService service = SpringUtil.getBean(RedisService.class);

    private RedisHelper() {
    }

    public static RedisService getInstance() {
        if (helper == null) {
            helper = new RedisHelper();
        }
        return helper.service;
    }
}
