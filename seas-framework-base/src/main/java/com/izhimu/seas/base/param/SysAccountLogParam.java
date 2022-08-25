package com.izhimu.seas.base.param;

import com.izhimu.seas.mybatis.annotation.OrderBy;
import com.izhimu.seas.mybatis.annotation.Search;
import com.izhimu.seas.mybatis.enums.SearchType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志视图层
 *
 * @author haoran
 * @version v1.0
 */
@Data
public class SysAccountLogParam implements Serializable {

    /**
     * 登录时间
     */
    @OrderBy(asc = false)
    private LocalDateTime loginTime;
    /**
     * 登录IP
     */
    @Search
    private String loginIp;
    /**
     * 登录地址
     */
    @Search
    private String loginAddress;
    /**
     * 用户ID
     */
    @Search(type = SearchType.EQUALS)
    private Long userId;
    /**
     * 账号ID
     */
    @Search(type = SearchType.EQUALS)
    private Long accountId;
    /**
     * 登录时间
     */
    @Search(name = "login_time", type = SearchType.GE)
    private LocalDateTime loginTimeStart;
    /**
     * 登录时间
     */
    @Search(name = "login_time", type = SearchType.LE)
    private LocalDateTime loginTimeEnd;
    /**
     * 状态
     * 0.登录，1.退出，2.密码错误，3.多次重试，4.禁用，5.登录失败
     */
    @Search(type = SearchType.EQUALS)
    private Integer status;
}
