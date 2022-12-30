package com.izhimu.seas.base.param;

import com.izhimu.seas.mybatis.annotation.OrderBy;
import com.izhimu.seas.mybatis.annotation.Search;
import com.izhimu.seas.mybatis.enums.SearchType;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户账号实体
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysAccountParam implements Serializable {

    @OrderBy(asc = false)
    private Long id;
    /**
     * 用户名
     */
    @Search
    private String userAccount;
    /**
     * 用户类型
     */
    @Search(type = SearchType.EQUALS)
    private Integer typeCode;
    /**
     * 状态
     * 0、正常 1、过期 2、锁定 3、密码过期
     */
    @Search(type = SearchType.EQUALS)
    private Integer status;
}
