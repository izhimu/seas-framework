package com.izhimu.seas.base.service;

import com.izhimu.seas.base.entity.SysAccountLog;
import com.izhimu.seas.base.param.SysAccountLogParam;
import com.izhimu.seas.base.vo.SysAccountLogVO;
import com.izhimu.seas.core.dto.LoginDTO;
import com.izhimu.seas.data.entity.Pagination;
import com.izhimu.seas.data.service.IBaseService;

/**
 * 登录日志服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysAccountLogService extends IBaseService<SysAccountLog> {

    /**
     * 分页查询
     *
     * @param page  Pagination
     * @param param SysAccountLogParam
     * @return Pagination<SysAccountLogVO>
     */
    Pagination<SysAccountLogVO> findPage(Pagination<SysAccountLog> page, SysAccountLogParam param);

    /**
     * 保存日志
     *
     * @param dto    LoginDTO
     * @param status 状态 0.登录，1.退出，2.密码错误，3.多次重试，4.禁用，5.登录失败
     */
    void saveLog(LoginDTO dto, int status);

    /**
     * 获取VO
     *
     * @param id Long
     * @return SysAccountLogVO
     */
    SysAccountLogVO get(Long id);
}
