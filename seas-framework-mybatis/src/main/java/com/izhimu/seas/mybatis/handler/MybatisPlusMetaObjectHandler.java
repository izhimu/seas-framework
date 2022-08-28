package com.izhimu.seas.mybatis.handler;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 实现BaseEntity中自动添加通用字段信息
 *
 * @author haoran
 * @version 1.0
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    private static final String ID = "id";
    private static final String CREATE_BY = "createdBy";
    private static final String UPDATE_BY = "updatedBy";
    private static final String CREATE_TIME = "createdTime";
    private static final String UPDATE_TIME = "updatedTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(ID)) {
            this.strictInsertFill(metaObject, ID, Long.class, IdUtil.getSnowflakeNextId());
        }

        if (metaObject.hasSetter(CREATE_TIME)) {
            this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        }
        if (metaObject.hasSetter(CREATE_BY)) {
            this.strictInsertFill(metaObject, CREATE_BY, Long.class, 0L);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        }

        if (metaObject.hasSetter(UPDATE_BY)) {
            this.strictInsertFill(metaObject, UPDATE_BY, Long.class, 0L);
        }
    }
}
