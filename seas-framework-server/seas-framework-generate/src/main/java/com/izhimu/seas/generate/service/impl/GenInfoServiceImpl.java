package com.izhimu.seas.generate.service.impl;

import com.izhimu.seas.generate.entity.GenInfo;
import com.izhimu.seas.generate.service.GenDatasourceService;
import com.izhimu.seas.generate.service.GenInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 代码生成服务层实现
 *
 * @author haoran
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GenInfoServiceImpl implements GenInfoService {

    @Resource
    private GenDatasourceService datasourceService;

    @Override
    public GenInfo getInfo(Long sourceId, String table) {
        String tableComment = datasourceService.tableComment(sourceId, table);
        List<Map<String, Object>> fields = datasourceService.fields(sourceId, table);
        return null;
    }
}
