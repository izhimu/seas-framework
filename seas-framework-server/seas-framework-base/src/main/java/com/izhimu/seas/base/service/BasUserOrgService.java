package com.izhimu.seas.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.izhimu.seas.base.entity.BasUserOrg;

import java.io.Serializable;
import java.util.List;

/**
 * 用户组织中间服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface BasUserOrgService extends IService<BasUserOrg> {

    /**
     * 根据用户ID获取部门ID
     *
     * @param userId 用户ID
     * @return 部门ID
     */
    Long getOrgId(Long userId);

    /**
     * 根据用户ID删除
     *
     * @param userId 用户ID
     * @return boolean
     */
    boolean removeByUserId(Serializable userId);
}
