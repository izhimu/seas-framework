package com.izhimu.seas.base.param;

import com.izhimu.seas.mybatis.annotation.OrderBy;
import com.izhimu.seas.mybatis.annotation.Search;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色参数
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysRoleParam implements Serializable {

    @OrderBy(asc = false)
    private Long id;

    /**
     * 角色名称
     */
    @Search
    private String roleName;
}
