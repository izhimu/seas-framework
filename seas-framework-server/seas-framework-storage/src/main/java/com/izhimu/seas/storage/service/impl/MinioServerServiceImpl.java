package com.izhimu.seas.storage.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.archiver.Archiver;
import com.izhimu.seas.core.utils.LogUtil;
import com.izhimu.seas.storage.config.MinioConfig;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.service.FileServerService;
import com.izhimu.seas.storage.service.StoFileService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

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
        final File tempFile = FileUtil.createTempFile("", ".tmp", false);
        try (Archiver archiver = CompressUtil.createArchiver(CharsetUtil.CHARSET_UTF_8, ArchiveStreamFactory.ZIP, tempFile)) {
            for (StoFile file : files) {
                GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                        .bucket(minioConfig.getBucket())
                        .object(file.getFilePath())
                        .build());
                File temp = new File(Files.createTempDirectory("") + File.separator + file.getFileName() + "." + file.getFileSuffix());
                IoUtil.copy(new ByteArrayInputStream(object.readAllBytes()), new FileOutputStream(temp));
                archiver.add(temp);
            }
            archiver.finish();
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
