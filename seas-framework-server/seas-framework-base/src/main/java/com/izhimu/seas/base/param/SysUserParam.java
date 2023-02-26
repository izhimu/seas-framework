package com.izhimu.seas.base.param;

import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.enums.SearchType;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户参数
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysUserParam implements Serializable {

    @OrderBy
    private Long id;
    /**
     * 用户昵称
     */
    @Search
    private String userName;
    /**
     * 用户性别
     * 0.保密 1.男 2.女
     */
    @Search(type = SearchType.EQUALS)
    private Integer userSex;
    /**
     * 手机号
     */
    @Search
    private String mobile;
    /**
     * 邮箱
     */
    @Search
    private String email;
    /**
     * 状态
     * 0.正常 1.禁用
     */
    @Search(type = SearchType.EQUALS)
    private Integer status;
}
