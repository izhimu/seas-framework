package com.izhimu.seas.generate.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
                    if (Objects.nonNull(v.getAssetsData())) {
                        v.setAssetsDataStr(new String(v.getAssetsData(), StandardCharsets.UTF_8));
                    }
                }).toList();
    }

    @Override
    public boolean assetsSave(List<GenTemplateAssets> assetsList) {
        Map<String, String> languageMap = new HashMap<>();
        languageMap.put("java", "java");
        languageMap.put("js", "javascript");
        languageMap.put("ts", "typescript");
        languageMap.put("vue", "typescript");
        languageMap.put("css", "css");
        languageMap.put("json", "json");
        languageMap.put("sql", "sql");
        languageMap.put("rs", "rust");
        assetsList.stream()
                .map(GenTemplateAssets::getTemplateId)
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(id -> templateAssetsService.lambdaUpdate()
                        .eq(GenTemplateAssets::getTemplateId, id)
                        .remove());
        assetsList
                .stream()
                .peek(v -> {
                    if (CharSequenceUtil.isNotBlank(v.getAssetsDataStr())) {
                        v.setAssetsData(v.getAssetsDataStr().getBytes(StandardCharsets.UTF_8));
                    }
                    if (CharSequenceUtil.isNotBlank(v.getAssetsName())) {
                        v.setAssetsType(languageMap.getOrDefault(FileUtil.getSuffix(v.getAssetsName()), ""));
                    }
                })
                .forEach(templateAssetsService::save);
        return true;
    }
}
