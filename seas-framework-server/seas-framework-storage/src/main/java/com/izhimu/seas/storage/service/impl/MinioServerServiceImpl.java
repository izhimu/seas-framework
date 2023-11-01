package com.izhimu.seas.storage.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.storage.config.MinioConfig;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.service.FileServerService;
import com.izhimu.seas.storage.service.StoFileService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.izhimu.seas.storage.service.impl.StoFileServiceImpl.BASE_URL;

/**
 * 文件服务实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
public class MinioServerServiceImpl implements FileServerService {

    private MinioClient minioClient;

    @Resource
    private StoFileService fileService;
    @Resource
    private MinioConfig minioConfig;

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
            log.error(LogUtil.format("FileStorage", "Error"), e);
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
            log.error(LogUtil.format("FileStorage", "Error"), e);
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
            log.error(LogUtil.format("FileStorage", "Error"), e);
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
            log.error(LogUtil.format("FileStorage", "Error"), e);
        }
    }

    @Override
    public void previewAsStream(Long id, OutputStream os) throws FileNotFoundException {
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
            log.error(LogUtil.format("FileStorage", "Error"), e);
        }
    }

    @Override
    public void previewAsHttp(Long id, HttpServletResponse response) throws FileNotFoundException {
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
            log.error(LogUtil.format("FileStorage", "Error"), e);
        }
    }

    @Override
    public StoFile upload(StoFile file, InputStream is) {
        createFilePath(file);
        createStorageType(file);
        createFileSize(file, is);
        try (is) {
            minioClient().putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(file.getFilePath())
                    .stream(is, is.available(), -1)
                    .contentType(file.getContentType())
                    .build());
            fileService.save(file);
        } catch (Exception e) {
            log.error(LogUtil.format("FileStorage", "Error"), e);
        }
        file.setFileUrl(BASE_URL.concat(String.valueOf(file.getId())));
        return file;
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

    /**
     * 创建ID
     *
     * @param file SysFile
     */
    private void createId(StoFile file) {
        if (Objects.isNull(file.getId())) {
            file.setId(IdUtil.getSnowflakeNextId());
        }
    }

    /**
     * 生成文件路径
     *
     * @param file SysFile
     */
    private void createFilePath(StoFile file) {
        createId(file);
        if (Objects.isNull(file.getFilePath())) {
            LocalDateTime now = LocalDateTime.now();
            String str = now.getYear() +
                    "/" +
                    now.getMonthValue() +
                    "/" +
                    now.getDayOfMonth() +
                    "/" +
                    file.getId();
            file.setFilePath(str);
        }
    }

    /**
     * 生成存储类型
     *
     * @param file SysFile
     */
    private void createStorageType(StoFile file) {
        if (Objects.isNull(file.getStorageType())) {
            String str = "MINIO:" + minioConfig.getBucket();
            file.setStorageType(str);
        }
    }

    /**
     * 生成文件大小
     *
     * @param file SysFile
     * @param is   InputStream
     */
    private void createFileSize(StoFile file, InputStream is) {
        if (Objects.isNull(file.getFileSize())) {
            try {
                file.setFileSize((long) is.available());
            } catch (IOException e) {
                log.error(LogUtil.format("FileStorage", "Error"), e);
                file.setFileSize(0L);
            }
        }
    }
}

