package com.izhimu.seas.generate.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.generate.entity.GenTemplate;
import com.izhimu.seas.generate.mapper.GenTemplateMapper;
import com.izhimu.seas.generate.service.GenTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 模板服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GenTemplateServiceImpl extends BaseServiceImpl<GenTemplateMapper, GenTemplate> implements GenTemplateService {
}
