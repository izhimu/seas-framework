package com.izhimu.seas.base.param;

import com.izhimu.seas.data.annotation.OrderBy;
import com.izhimu.seas.data.annotation.Search;
import com.izhimu.seas.data.enums.SearchType;
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
public class SysLogParam implements Serializable {

    /**
     * 请求地址
     */
    @Search
    private String requestUrl;
    /**
     * 请求方式
     */
    @Search(type = SearchType.EQUALS)
    private String method;
    /**
     * 请求时间
     */
    @OrderBy(asc = false)
    private LocalDateTime requestDate;
    @Search(name = "request_date", type = SearchType.GE)
    private LocalDateTime requestDateStart;
    @Search(name = "request_date", type = SearchType.LE)
    private LocalDateTime requestDateEnd;
    /**
     * 操作人
     */
    @Search(type = SearchType.EQUALS)
    private Long userId;
    /**
     * 日志名称
     */
    @Search
    private String logName;
    /**
     * 请求状态
     */
    @Search
    private String status;
}
