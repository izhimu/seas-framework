package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.SysLog;
import com.izhimu.seas.base.param.SysLogParam;
import com.izhimu.seas.base.vo.SysLogVO;
import com.izhimu.seas.core.dto.SysLogDTO;
import com.izhimu.seas.data.entity.Pagination;
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
     * @param dto {@link SysLogDTO SysLogDTO}
     */
    void saveLog(SysLogDTO dto);

    /**
     * 分页查询数据
     *
     * @param page 分页参数 {@link Pagination Pagination}
     * @param dto  查询参数 {@link SysLogParam SysLogParam}
     * @return 分页数据 {@link SysLogVO SysLogVO}
     */
    Pagination<SysLogVO> findPage(Pagination<SysLog> page, SysLogParam dto);

    /**
     * 查询详情
     *
     * @param id Long
     * @return {@link SysLogVO SysLogVO}
     */
    SysLogVO get(Long id);
}
