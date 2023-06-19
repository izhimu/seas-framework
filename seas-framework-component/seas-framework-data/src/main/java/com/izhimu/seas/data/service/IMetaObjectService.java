package com.izhimu.seas.data.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 数据填充服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface IMetaObjectService extends MetaObjectHandler {

    String ID = "id";
    String CREATE_BY = "createdBy";
    String UPDATE_BY = "updatedBy";
    String CREATE_TIME = "createdTime";
    String UPDATE_TIME = "updatedTime";

    /**
     * insertFill
     *
     * @param metaObject MetaObject
     */
    @Override
    default void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter(ID)) {
            this.strictInsertFill(metaObject, ID, Long.class, IdUtil.getSnowflakeNextId());
        }

        if (metaObject.hasSetter(CREATE_TIME)) {
            this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, LocalDateTime.now());
        }
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        }
        Long userId = getUserId();
        if (metaObject.hasSetter(CREATE_BY) && Objects.nonNull(userId)) {
            this.strictInsertFill(metaObject, CREATE_BY, Long.class, userId);
        }
        if (metaObject.hasSetter(UPDATE_BY) && Objects.nonNull(userId)) {
            this.strictInsertFill(metaObject, UPDATE_BY, Long.class, userId);
        }
    }

    /**
     * updateFill
     *
     * @param metaObject MetaObject
     */
    @Override
    default void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter(UPDATE_TIME)) {
            this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, LocalDateTime.now());
        }
        Long userId = getUserId();
        if (metaObject.hasSetter(UPDATE_BY) && Objects.nonNull(userId)) {
            this.strictInsertFill(metaObject, UPDATE_BY, Long.class, userId);
        }
    }

    /**
     * 获取用户ID
     *
     * @return Long
     */
    Long getUserId();
}
