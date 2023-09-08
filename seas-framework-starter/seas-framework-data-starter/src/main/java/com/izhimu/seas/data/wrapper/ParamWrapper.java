package com.izhimu.seas.data.wrapper;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.core.entity.DataPermission;
import com.izhimu.seas.core.entity.User;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Permission;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.enums.SearchType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 参数条件构造器
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("unused")
@Slf4j
public class ParamWrapper<T> {

    private final QueryChainWrapper<T> wrapper;

    private Object entity;

    private ParamWrapper(IService<T> service) {
        this.wrapper = service.query();
    }

    /**
     * 查询目标
     *
     * @param service IService<T>
     * @return SearchWrapper<T>
     */
    public static <T> ParamWrapper<T> query(IService<T> service) {
        return new ParamWrapper<>(service);
    }

    /**
     * 查询条件传入
     *
     * @param entity 条件
     * @return SearchWrapper<T>
     */
    public ParamWrapper<T> param(Object entity) {
        this.entity = entity;
        getFieldAnnotationValue(entity.getClass(), entity);
        return this;
    }

    /**
     * 默认排序
     *
     * @return SearchWrapper<T>
     */
    public ParamWrapper<T> orderBy() {
        Map<String, Boolean> orderByField = getOrderByField();
        if (orderByField.isEmpty()) {
            return this;
        }
        orderByField.forEach((k, v) -> wrapper.orderBy(true, v, k));
        return this;
    }

    /**
     * 排序条件传入
     *
     * @param page 分页排序
     * @return SearchWrapper<T>
     */
    public ParamWrapper<T> orderBy(IPage<T> page) {
        Map<String, Boolean> orderByField = getOrderByField();
        List<OrderItem> orders = page.orders();
        if (orders.isEmpty() && orderByField.isEmpty()) {
            return this;
        }
        orders.forEach(v -> orderByField.put(v.getColumn(), v.isAsc()));
        orderByField.forEach((k, v) -> wrapper.orderBy(true, v, k));
        return this;
    }

    /**
     * 开启数据权限包装
     *
     * @return 查询包装器
     */
    public ParamWrapper<T> permissions() {
        Permission config = getDataPermission();
        if (Objects.isNull(config)) {
            return this;
        }
        User user = StpUtil.getSession().getModel(SaSession.USER, User.class);
        DataPermission dataPermission = user.getDataAuth();
        if (Objects.equals(1, dataPermission.simpleType())) {
            wrapper.in(config.orgField(), dataPermission.getAuthList());
        } else if (Objects.equals(2, dataPermission.simpleType())) {
            wrapper.in(config.userField(), dataPermission.getAuthList());
        }
        return this;
    }

    /**
     * 获取条件构造
     *
     * @return QueryChainWrapper<T>
     */
    public QueryChainWrapper<T> wrapper() {
        return wrapper;
    }

    /**
     * 获取字段
     *
     * @param clazz  类
     * @param entity 实体
     */
    private void getFieldAnnotationValue(Class<?> clazz, Object entity) {
        try {
            // 获取所有的字段
            Field[] fields = ReflectUtil.getFields(clazz);
            for (Field f : fields) {
                // 判断字段注解是否存在
                boolean searchAnnotationPresent = f.isAnnotationPresent(Search.class);
                if (searchAnnotationPresent) {
                    Search search = f.getAnnotation(Search.class);
                    SearchType type = search.type();
                    String name = f.getName();
                    Object value = ReflectUtil.getFieldValue(entity, f);
                    boolean b = value instanceof String ? StrUtil.isNotBlank((String) value) : Objects.nonNull(value);
                    if (b) {
                        searchStructure(
                                type,
                                StrUtil.isNotBlank(search.name()) ? search.name() : CharSequenceUtil.toUnderlineCase(name)
                                , value
                        );
                    }
                }
            }
        } catch (Exception e) {
            log.error(LogUtil.format("ParamWrapper", "Error"), e);
        }
    }

    /**
     * 获取排序字段
     */
    private Map<String, Boolean> getOrderByField() {
        try {
            // 获取所有的字段
            Field[] fields = ReflectUtil.getFields(entity.getClass());
            Map<String, Boolean> map = new HashMap<>(fields.length);
            for (Field f : fields) {
                // 判断字段注解是否存在
                boolean orderAnnotationPresent = f.isAnnotationPresent(OrderBy.class);
                if (orderAnnotationPresent) {
                    OrderBy orderBy = f.getAnnotation(OrderBy.class);
                    boolean asc = orderBy.asc();
                    String name = CharSequenceUtil.toUnderlineCase(f.getName());
                    map.put(name, asc);
                }
            }
            return map;
        } catch (Exception e) {
            log.error(LogUtil.format("ParamWrapper", "Error"), e);
            return new HashMap<>(16);
        }
    }

    /**
     * 构造搜索条件
     *
     * @param type  类型
     * @param name  字段名
     * @param value 字段值
     */
    private void searchStructure(SearchType type, String name, Object value) {
        switch (type) {
            case EQUALS -> wrapper.eq(name, value);
            case LIKE -> wrapper.like(name, value);
            case LIKE_L -> wrapper.likeLeft(name, value);
            case LIKE_R -> wrapper.likeRight(name, value);
            case IN -> wrapper.in(name, (Collection<?>) value);
            case NOT_IN -> wrapper.notIn(name, (Collection<?>) value);
            case GT -> wrapper.gt(name, value);
            case GE -> wrapper.ge(name, value);
            case LT -> wrapper.lt(name, value);
            case LE -> wrapper.le(name, value);
            default -> {
            }
        }
    }

    private Permission getDataPermission() {
        try {
            return entity.getClass().getAnnotation(Permission.class);
        } catch (Exception e) {
            log.error(LogUtil.format("ParamWrapper", "Error"), e);
            return null;
        }
    }
}
