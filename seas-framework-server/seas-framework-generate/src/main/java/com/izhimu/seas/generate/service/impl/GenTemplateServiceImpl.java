package com.izhimu.seas.generate.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.generate.entity.GenTemplate;
import com.izhimu.seas.generate.entity.GenTemplateAssets;
import com.izhimu.seas.generate.mapper.GenTemplateMapper;
import com.izhimu.seas.generate.service.GenTemplateAssetsService;
import com.izhimu.seas.generate.service.GenTemplateService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 模板服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GenTemplateServiceImpl extends BaseServiceImpl<GenTemplateMapper, GenTemplate> implements GenTemplateService {

    @Resource
    private GenTemplateAssetsService templateAssetsService;

    @Override
    public List<GenTemplateAssets> assetsList(Long id) {
        return templateAssetsService.lambdaQuery()
                .eq(GenTemplateAssets::getTemplateId, id)
                .list().stream()
                .peek(v -> {
                    if (v.getAssetsData() instanceof byte[] bytes) {
                        v.setAssetsData(new String(bytes, StandardCharsets.UTF_8));
                    }
                }).toList();
    }

    @Override
    public boolean assetsSave(List<GenTemplateAssets> assetsList) {
        assetsList.stream()
                .map(GenTemplateAssets::getTemplateId)
                .filter(ObjectUtil::isNotNull)
                .findFirst()
                .ifPresent(id -> templateAssetsService.lambdaUpdate()
                        .eq(GenTemplateAssets::getTemplateId, id)
                        .remove());
        assetsList
                .stream()
                .peek(v -> {
                    if (v.getAssetsData() instanceof String data) {
                        v.setAssetsData(data.getBytes(StandardCharsets.UTF_8));
                    }
                })
                .forEach(templateAssetsService::save);
        return true;
    }
}
