package com.izhimu.seas.base.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.izhimu.seas.base.entity.BasAccountLog;
import com.izhimu.seas.core.entity.Login;
import com.izhimu.seas.data.service.IBaseService;

/**
 * 登录日志服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasAccountLogService extends IBaseService<BasAccountLog> {

    /**
     * 分页查询
     *
     * @param page  Pagination
     * @param param SysAccountLog
     * @return Pagination<SysAccountLog>
     */
    Page<BasAccountLog> findPage(Page<BasAccountLog> page, BasAccountLog param);

    /**
     * 保存日志
     *
     * @param login    Login
     * @param status 状态 0.登录，1.退出，2.密码错误，3.多次重试，4.禁用，5.登录失败
     */
    void saveLog(Login login, int status);

    /**
     * 获取
     *
     * @param id Long
     * @return SysAccountLog
     */
    BasAccountLog get(Long id);

    /**
     * 清理日志
     *
     * @return boolean
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean cleanLog();
}
