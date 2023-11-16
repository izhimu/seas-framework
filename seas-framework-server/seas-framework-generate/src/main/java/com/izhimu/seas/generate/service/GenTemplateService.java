package com.izhimu.seas.generate.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.generate.entity.GenTemplate;
import com.izhimu.seas.generate.entity.GenTemplateAssets;

import java.util.List;

/**
 * 模板服务层接口
 *
 * @author haoran
 * @version v1.0
 */
public interface GenTemplateService extends IBaseService<GenTemplate> {

    /**
     * 获取模板资源
     *
     * @param id ID
     * @return GenTemplateAssets
     */
    List<GenTemplateAssets> assetsList(Long id);

    /**
     * 保存模板资源
     *
     * @param assetsList List<GenTemplateAssets>
     * @return boolean
     */
    boolean assetsSave(List<GenTemplateAssets> assetsList);
}
