package com.izhimu.seas.cache.helper;

import cn.hutool.extra.spring.SpringUtil;
import com.izhimu.seas.cache.service.CacheService;

import java.util.Objects;

/**
 * RedisHelper
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
public class CacheHelper {

    private static CacheHelper helper;

    private final CacheService service = SpringUtil.getBean(CacheService.class);

    private CacheHelper() {
    }

    public static CacheService get() {
        if (Objects.isNull(helper)) {
            helper = new CacheHelper();
        }
        return helper.service;
    }
}
