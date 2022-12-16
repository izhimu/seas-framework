package com.izhimu.seas.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.izhimu.seas.base.entity.SysUser;
import org.springframework.stereotype.Repository;

/**
 * 用户映射层
 *
 * @author haoran
 * @version v1.0
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
}
