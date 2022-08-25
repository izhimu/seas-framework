package com.izhimu.seas.mybatis.entity;

import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.function.Supplier;

/**
 * 分页对象
 *
 * @author Haoran
 * @version v1.0
 * @date 2022/3/9 9:20
 */
public class Pagination<T> extends Page<T> {

    public static <T, E> Pagination<E> of(Pagination<T> page, Supplier<E> target) {
        Pagination<E> ofPage = new Pagination<>();
        ofPage.total = page.total;
        ofPage.size = page.size;
        ofPage.current = page.current;
        ofPage.orders = page.orders;
        ofPage.optimizeCountSql = page.optimizeCountSql;
        ofPage.searchCount = page.searchCount;
        ofPage.countId = page.countId;
        ofPage.maxLimit = page.maxLimit;
        ofPage.records = CglibUtil.copyList(page.records, target);
        return ofPage;
    }
}
