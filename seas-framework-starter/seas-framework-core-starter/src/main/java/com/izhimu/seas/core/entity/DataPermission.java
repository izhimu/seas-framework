package com.izhimu.seas.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 数据权限实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class DataPermission implements Serializable {

    public static final Long NO_PERMISSION = -9999L;

    /**
     * 类型
     * 0全部数据权限、1自定义数据权限、2本部门及下级部门数据权限、3本部门数据权限、4仅自己数据权限
     */
    private Integer type;
    /**
     * 权限列表
     */
    private List<Long> authList;

    public static DataPermission def() {
        DataPermission dataPermission = new DataPermission();
        dataPermission.setType(4);
        dataPermission.setAuthList(Collections.singletonList(NO_PERMISSION));
        return dataPermission;
    }
}
