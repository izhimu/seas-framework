package com.izhimu.seas.storage.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ZipUtil;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.storage.config.MinioConfig;
import com.izhimu.seas.storage.convert.DefFileConvert;
import com.izhimu.seas.storage.convert.IFileConvert;
import com.izhimu.seas.storage.entity.FileInfo;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.service.FileService;
import com.izhimu.seas.storage.service.StoFileService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.izhimu.seas.storage.constant.PreviewConst.PDF_CONVERT_MAP;
import static com.izhimu.seas.storage.constant.PreviewConst.PNG_CONVERT_MAP;

/**
 * 文件服务实现
 *
 * @author haoran
 * @version v1.0
 */
@SuppressWarnings("DuplicatedCode")
@Slf4j
@Service
@ConditionalOnProperty(prefix = "seas.storage", name = "type", havingValue = "minio")
public class MinioFileServiceImpl implements FileService {

    private MinioClient minioClient;

    @Resource
    private StoFileService fileService;
    @Resource
    private MinioConfig minioConfig;

    public MinioFileServiceImpl(StoFileService fileService, MinioConfig minioConfig) {
        this.fileService = fileService;
        this.minioConfig = minioConfig;
    }

    @Override
    public void downloadAsStream(Long id, OutputStream os) throws FileNotFoundException {
        StoFile file = fileService.getFile(id);
        if (Objects.isNull(file)) {
            throw new FileNotFoundException();
        }
        try (GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                .bucket(minioConfig.getBucket())
                .object(file.getFilePath())
                .build());
             os) {
            IoUtil.copy(object, os);
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Download as stream error"), e);
        }
    }

    @Override
    public void downloadAsHttp(Long id, HttpServletResponse response) throws FileNotFoundException {
        StoFile file = fileService.getFile(id);
        if (Objects.isNull(file)) {
            throw new FileNotFoundException();
        }
        try (GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                .bucket(minioConfig.getBucket())
                .object(file.getFilePath())
                .build());
             ServletOutputStream output = response.getOutputStream()) {
            String fileName = file.getFileName().concat(".").concat(file.getFileSuffix());
            response.setContentType(file.getContentType());
            response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            IoUtil.copy(object, output);
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Download as http error"), e);
        }
    }

    @Override
    public void downloadZipAsStream(Long bindId, OutputStream os) throws FileNotFoundException {
        List<StoFile> files = fileService.getFiles(bindId);
        if (files.isEmpty()) {
            throw new FileNotFoundException();
        }
        try {
            List<String> pathList = new ArrayList<>();
            List<InputStream> inList = new ArrayList<>();
            for (StoFile file : files) {
                GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                        .bucket(minioConfig.getBucket())
                        .object(file.getFilePath())
                        .build());
                pathList.add(file.getFileName().concat(".").concat(file.getFileSuffix()));
                inList.add(object);
            }
            ZipUtil.zip(os, ArrayUtil.toArray(pathList, String.class), ArrayUtil.toArray(inList, InputStream.class));
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Download zip as stream error"), e);
        }
    }

    @Override
    public void downloadZipAsHttp(Long bindId, HttpServletResponse response) throws FileNotFoundException {
        List<StoFile> files = fileService.getFiles(bindId);
        if (files.isEmpty()) {
            throw new FileNotFoundException();
        }
        try {
            List<String> pathList = new ArrayList<>();
            List<InputStream> inList = new ArrayList<>();
            for (StoFile file : files) {
                GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                        .bucket(minioConfig.getBucket())
                        .object(file.getFilePath())
                        .build());
                pathList.add(file.getFileName().concat(".").concat(file.getFileSuffix()));
                inList.add(object);
            }
            String fileName = "合并下载_".concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))).concat(".zip");
            response.setContentType("application/zip");
            response.addHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            ZipUtil.zip(response.getOutputStream(), ArrayUtil.toArray(pathList, String.class), ArrayUtil.toArray(inList, InputStream.class));
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Download zip as http error"), e);
        }
    }

    @Override
    public void previewAsStream(Long id, String type, OutputStream os, Map<String, Object> param) throws FileNotFoundException {
        StoFile file = fileService.getFile(id);
        if (Objects.isNull(file)) {
            throw new FileNotFoundException();
        }
        try (GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                .bucket(minioConfig.getBucket())
                .object(file.getFilePath())
                .build()); os) {
            if (Objects.equals("img", type)) {
                Class<? extends IFileConvert> convertClass = PNG_CONVERT_MAP.getOrDefault(file.getContentType(), DefFileConvert.class);
                IFileConvert convert = convertClass.getDeclaredConstructor().newInstance();
                convert.convert(object, os, param);
            } else if (Objects.equals("pdf", type)) {
                Class<? extends IFileConvert> convertClass = PDF_CONVERT_MAP.getOrDefault(file.getContentType(), DefFileConvert.class);
                IFileConvert convert = convertClass.getDeclaredConstructor().newInstance();
                convert.convert(object, os, param);
            }
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Preview as stream error"), e);
        }
    }

    @Override
    public void previewAsHttp(Long id, String type, HttpServletResponse response, Map<String, Object> param) throws FileNotFoundException {
        StoFile file = fileService.getFile(id);
        if (Objects.isNull(file)) {
            throw new FileNotFoundException();
        }
        try (GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                .bucket(minioConfig.getBucket())
                .object(file.getFilePath())
                .build());
             ServletOutputStream output = response.getOutputStream()) {
            if (Objects.equals("img", type)) {
                response.setContentType("application/png");
                Class<? extends IFileConvert> convertClass = PNG_CONVERT_MAP.getOrDefault(file.getContentType(), DefFileConvert.class);
                IFileConvert convert = convertClass.getDeclaredConstructor().newInstance();
                convert.convert(object, output, param);
            } else if (Objects.equals("pdf", type)) {
                response.setContentType("application/pdf");
                Class<? extends IFileConvert> convertClass = PDF_CONVERT_MAP.getOrDefault(file.getContentType(), DefFileConvert.class);
                IFileConvert convert = convertClass.getDeclaredConstructor().newInstance();
                convert.convert(object, output, param);
            }
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Preview as http error"), e);
        }
    }

    @Override
    public StoFile upload(StoFile file, InputStream is) {
        createFilePath(file);
        file.setStorageType("MINIO:" + minioConfig.getBucket());
        try (is) {
            calculateFileSize(file, is);
            minioClient().putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(file.getFilePath())
                    .stream(is, is.available(), -1)
                    .contentType(file.getContentType())
                    .build());
            fileService.save(file);
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Upload error"), e);
        }
        return file;
    }

    @Override
    public FileInfo previewInfo(Long id) throws FileNotFoundException {
        StoFile file = fileService.getFile(id);
        if (Objects.isNull(file)) {
            throw new FileNotFoundException();
        }
        try (GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                .bucket(minioConfig.getBucket())
                .object(file.getFilePath())
                .build())) {
            Class<? extends IFileConvert> convertClass = PNG_CONVERT_MAP.getOrDefault(file.getContentType(), DefFileConvert.class);
            IFileConvert convert = convertClass.getDeclaredConstructor().newInstance();
            FileInfo fileInfo = convert.getFileInfo(object);
            if (Objects.isNull(fileInfo)) {
                fileInfo = new FileInfo();
            }
            fileInfo.setName(file.getFileName());
            fileInfo.setSuffix(file.getFileSuffix());
            fileInfo.setSize(file.getFileSize());
            fileInfo.setContentType(file.getContentType());
            return fileInfo;
        } catch (Exception e) {
            log.error(LogUtil.format("MinioFileService", "Get file info error"), e);
            return null;
        }
    }

    /**
     * 获取Minio客户端
     *
     * @return MinioClient
     */
    private MinioClient minioClient() {
        if (Objects.isNull(minioClient)) {
            minioClient = MinioClient.builder()
                    .endpoint(minioConfig.getEndPoint())
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();
        }
        return minioClient;
    }
}

