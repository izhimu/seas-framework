package com.izhimu.seas.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasLog;
import com.izhimu.seas.core.entity.Log;
import com.izhimu.seas.data.service.IBaseService;

/**
 * 操作日志服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasLogService extends IBaseService<BasLog> {

    /**
     * 保存日志
     *
     * @param log {@link Log SysLogDTO}
     */
    void saveLog(Log log);

    /**
     * 分页查询数据
     *
     * @param page 分页参数 {@link Page Page}
     * @param log  查询参数 {@link BasLog SysLog}
     * @return 分页数据 {@link BasLog SysLog}
     */
    Page<BasLog> findPage(Page<BasLog> page, BasLog log);

    /**
     * 查询详情
     *
     * @param id Long
     * @return {@link BasLog SysLog}
     */
    BasLog get(Long id);

    /**
     * 清理日志
     *
     * @return boolean
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean cleanLog();
}
