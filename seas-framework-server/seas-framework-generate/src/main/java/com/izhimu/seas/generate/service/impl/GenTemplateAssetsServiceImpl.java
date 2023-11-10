package com.izhimu.seas.generate.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.generate.entity.GenTemplateAssets;
import com.izhimu.seas.generate.mapper.GenTemplateAssetsMapper;
import com.izhimu.seas.generate.service.GenTemplateAssetsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模板资源服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GenTemplateAssetsServiceImpl extends BaseServiceImpl<GenTemplateAssetsMapper, GenTemplateAssets> implements GenTemplateAssetsService {
}
