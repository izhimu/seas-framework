package com.izhimu.seas.generate.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.izhimu.seas.generate.db.enums.DbType;
import com.izhimu.seas.generate.entity.GenDatasource;
import com.izhimu.seas.generate.entity.GenFieldInfo;
import com.izhimu.seas.generate.entity.GenInfo;
import com.izhimu.seas.generate.entity.GenTemplateAssets;
import com.izhimu.seas.generate.service.GenDatasourceService;
import com.izhimu.seas.generate.service.GenInfoService;
import com.izhimu.seas.generate.service.GenTemplateAssetsService;
import com.izhimu.seas.generate.util.TypeUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.izhimu.seas.core.log.LogHelper.log;
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
    @Resource
    private GenTemplateAssetsService templateAssetsService;

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

    @Override
    public void create(GenInfo genInfo, HttpServletResponse response) {
        List<GenTemplateAssets> assetsList = preview(genInfo);
        String tempPath = FileUtil.getTmpDirPath().concat(IdUtil.nanoId()).concat(File.separator);
        try (ServletOutputStream os = response.getOutputStream()) {
            assetsList.forEach(assets -> {
                String filePath = tempPath.concat(assets.getOutPath());
                if (!filePath.endsWith(File.separator)) {
                    filePath = filePath.concat(File.separator);
                }
                filePath = filePath.concat(assets.getAssetsName());
                FileUtil.writeUtf8String(assets.getAssetsDataStr(), filePath);
            });
            String fileName = "代码生成_".concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))).concat(".zip");
            response.setContentType("application/zip");
            response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            File zip = ZipUtil.zip(tempPath);
            output(zip, os);
        } catch (IOException e) {
            log.error(e);
        } finally {
            FileUtil.del(tempPath);
        }
    }

    @Override
    public List<GenTemplateAssets> preview(GenInfo genInfo) {
        List<GenTemplateAssets> list = templateAssetsService.lambdaQuery()
                .eq(GenTemplateAssets::getTemplateId, genInfo.getTemplateId())
                .list();
        if (CollUtil.isEmpty(list) && CollUtil.isEmpty(genInfo.getFieldList())) {
            return Collections.emptyList();
        }
        TemplateEngine engine = TemplateUtil.createEngine();
        Map<String, Object> param = getParam(genInfo);
        list.forEach(v -> {
            v.setAssetsName(engine.getTemplate(v.getAssetsName()).render(param));
            v.setAssetsDataStr(engine.getTemplate(new String(v.getAssetsData(), StandardCharsets.UTF_8)).render(param));
            v.setOutPath(engine.getTemplate(v.getOutPath()).render(param));
        });
        return list;
    }

    private void output(File zip, OutputStream os) {
        try (FileInputStream is = new FileInputStream(zip)) {
            IoUtil.copy(is, os);
            IoUtil.flush(os);
        } catch (IOException e) {
            log.error(e);
        }
    }

    private Map<String, Object> getParam(GenInfo genInfo) {
        Map<String, Object> param = new HashMap<>();
        param.put("author", Optional.ofNullable(genInfo.getAuthor()).orElse(""));
        param.put("packageName", Optional.ofNullable(genInfo.getPackageName()).orElse(""));
        param.put("tableName", Optional.ofNullable(genInfo.getTableName()).orElse(""));
        param.put("tableDesc", Optional.ofNullable(genInfo.getTableDesc()).orElse(""));
        param.put("className", Optional.ofNullable(genInfo.getClassName()).orElse(""));
        param.put("fileName", CharSequenceUtil.lowerFirst(Optional.ofNullable(genInfo.getClassName()).orElse("")));
        param.put("pathName", "/".concat(CharSequenceUtil.toUnderlineCase(Optional.ofNullable(genInfo.getClassName()).orElse("")).replace("_", "/")));
        param.put("keyName", CharSequenceUtil.toUnderlineCase(Optional.ofNullable(genInfo.getClassName()).orElse("")).replace("_", "."));
        param.put("fieldList", Optional.ofNullable(genInfo.getFieldList()).orElse(Collections.emptyList()));
        extJavaParam(param, genInfo);
        return param;
    }

    private void extJavaParam(Map<String, Object> param, GenInfo genInfo) {
        List<GenFieldInfo> genFieldInfos = Optional.ofNullable(genInfo.getFieldList()).orElse(Collections.emptyList());
        param.put("importList", TypeUtil.getJavaImport(genFieldInfos.stream().map(GenFieldInfo::getJavaType).toList()));
        param.put("importSearch", genFieldInfos.stream().map(GenFieldInfo::getSearchable).anyMatch(v -> v == 1));
    }
}
