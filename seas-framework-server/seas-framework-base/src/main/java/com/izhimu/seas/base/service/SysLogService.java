package com.izhimu.seas.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.SysLog;
import com.izhimu.seas.core.web.entity.Log;
import com.izhimu.seas.data.service.IBaseService;

/**
 * 操作日志服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysLogService extends IBaseService<SysLog> {

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
     * @param log  查询参数 {@link SysLog SysLog}
     * @return 分页数据 {@link SysLog SysLog}
     */
    Page<SysLog> findPage(Page<SysLog> page, SysLog log);

    /**
     * 查询详情
     *
     * @param id Long
     * @return {@link SysLog SysLog}
     */
    SysLog get(Long id);
}
