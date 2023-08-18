package com.izhimu.seas.core.entity;

import com.izhimu.seas.core.enums.RefreshSessionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 刷新会话参数传输
 *
 * @author haoran
 * @version v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshSession implements Serializable {

    /**
     * ID
     */
    private Serializable id;
    /**
     * 类型
     */
    private RefreshSessionType type;

    public static RefreshSession user(Serializable id) {
        return new RefreshSession(id, RefreshSessionType.USER);
    }

    public static RefreshSession org(Serializable id) {
        return new RefreshSession(id, RefreshSessionType.ORG);
    }

    public static RefreshSession role(Serializable id) {
        return new RefreshSession(id, RefreshSessionType.ROLE);
    }

    public static RefreshSession menu(Serializable id) {
        return new RefreshSession(id, RefreshSessionType.MENU);
    }
}
