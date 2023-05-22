package com.izhimu.seas.base.service.impl;

import cn.hutool.extra.cglib.CglibUtil;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.izhimu.seas.base.entity.SysLog;
import com.izhimu.seas.base.mapper.SysLogMapper;
import com.izhimu.seas.base.param.SysLogParam;
import com.izhimu.seas.base.service.SysLogService;
import com.izhimu.seas.base.vo.SysLogVO;
import com.izhimu.seas.common.utils.TimeUtil;
import com.izhimu.seas.core.dto.SysLogDTO;
import com.izhimu.seas.data.entity.Pagination;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 操作日志服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Override
    public void saveLog(SysLogDTO dto) {
        if (Objects.isNull(dto)) {
            return;
        }
        SysLog copy = CglibUtil.copy(dto, SysLog.class);
        super.save(copy);
    }

    @Override
    public Pagination<SysLogVO> findPage(Pagination<SysLog> page, SysLogParam param) {
        QueryChainWrapper<SysLog> wrapper = super.paramQuery().param(param).orderBy().wrapper();
        wrapper.select("id", "request_url", "method", "result", "request_date", "user_name", "account", "account", "status", "runtime", "log_name");
        Pagination<SysLog> entityPage = wrapper.page(page);
        Pagination<SysLogVO> result = Pagination.of(entityPage, SysLogVO::new);
        result.getRecords().forEach(v -> v.setRuntimeValue(TimeUtil.millisecondsToStr(v.getRuntime())));
        return result;
    }

    @Override
    public SysLogVO get(Long id) {
        SysLogVO sysLogVO = super.get(id, SysLogVO.class);
        sysLogVO.setRuntimeValue(TimeUtil.millisecondsToStr(sysLogVO.getRuntime()));
        return sysLogVO;
    }
}
