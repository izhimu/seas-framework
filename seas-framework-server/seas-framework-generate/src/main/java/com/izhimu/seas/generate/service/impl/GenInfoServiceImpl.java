package com.izhimu.seas.generate.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import com.izhimu.seas.generate.db.enums.DbType;
import com.izhimu.seas.generate.entity.GenDatasource;
import com.izhimu.seas.generate.entity.GenFieldInfo;
import com.izhimu.seas.generate.entity.GenInfo;
import com.izhimu.seas.generate.service.GenDatasourceService;
import com.izhimu.seas.generate.service.GenInfoService;
import com.izhimu.seas.generate.util.TypeUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.izhimu.seas.generate.db.engine.AbstractDbEngine.*;

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
        GenDatasource datasource = datasourceService.getById(sourceId);
        DbType dbType = DbType.valueOf(datasource.getDsType());
        String tableComment = datasourceService.tableComment(sourceId, table);
        List<Map<String, Object>> fields = datasourceService.fields(sourceId, table);
        GenInfo info = new GenInfo();
        info.setSourceId(sourceId);
        info.setTableName(table);
        info.setTableDesc(tableComment);
        info.setClassName(CharSequenceUtil.upperFirst(CharSequenceUtil.toCamelCase(table)));
        info.setFieldList(fields.parallelStream()
                .map(v -> {
                    GenFieldInfo fieldInfo = new GenFieldInfo();
                    fieldInfo.setFieldName(Convert.toStr(v.get(COLUMN_NAME)));
                    fieldInfo.setShowName(Convert.toStr(v.get(COLUMN_COMMENT)));
                    fieldInfo.setFieldType(Convert.toStr(v.get(COLUMN_TYPE)));
                    fieldInfo.setIsPk(Boolean.TRUE.equals(Convert.toBool(v.get(IS_PK))) ? 1 : 0);
                    fieldInfo.setIsNull(Boolean.TRUE.equals(Convert.toBool(v.get(IS_NULL))) ? 1 : 0);
                    fieldInfo.setAttrName(CharSequenceUtil.toCamelCase(fieldInfo.getFieldName()));
                    fieldInfo.setJavaType(TypeUtil.getJavaType(fieldInfo.getFieldType(), dbType));
                    fieldInfo.setJsType(TypeUtil.getJsType(fieldInfo.getFieldType(), dbType));
                    fieldInfo.setInsertable(Objects.equals(0, fieldInfo.getIsPk()) ? 1 : 0);
                    fieldInfo.setListable(fieldInfo.getInsertable());
                    fieldInfo.setSearchable(fieldInfo.getInsertable());
                    fieldInfo.setSortable(fieldInfo.getInsertable());
                    return fieldInfo;
                })
                .toList());
        return info;
    }
}
