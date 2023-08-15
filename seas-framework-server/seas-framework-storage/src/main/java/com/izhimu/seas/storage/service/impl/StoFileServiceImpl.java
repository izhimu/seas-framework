package com.izhimu.seas.storage.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.archiver.Archiver;
import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.storage.config.MinioConfig;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.mapper.StoFileMapper;
import com.izhimu.seas.storage.service.StoFileService;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 文件信息服务层实现
 *
 * @author haoran
 * @version v1.0
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StoFileServiceImpl extends BaseServiceImpl<StoFileMapper, StoFile> implements StoFileService {

    private static final String BASE_URL = "/sto/file/";

    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    @Override
    public StoFile getFile(Long id) {
        Optional<StoFile> sysFile = this.lambdaQuery()
                .eq(StoFile::getDelTag, 0)
                .eq(StoFile::getId, id)
                .oneOpt();
        if (sysFile.isEmpty()) {
            return null;
        }
        StoFile file = sysFile.get();
        file.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat(String.valueOf(file.getId())));
        return file;
    }

    @Override
    public List<StoFile> getFiles(Long bindId) {
        List<StoFile> files = this.lambdaQuery()
                .eq(StoFile::getDelTag, 0)
                .eq(StoFile::getBindId, bindId)
                .list();
        files.forEach(file -> file.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat(String.valueOf(file.getId()))));
        return files;
    }

    @Override
    public StoFile getFilesToCompression(Long bindId) {
        StoFile file = new StoFile();
        file.setId(bindId);
        file.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat("bind/").concat(String.valueOf(file.getId())));
        return file;
    }

    @Override
    public InputStream download(Long id) {
        StoFile file = getFile(id);
        if (Objects.isNull(file)) {
            return null;
        }
        try {
            GetObjectResponse object = minioClient().getObject(GetObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(file.getFilePath())
                    .build());
            return new ByteArrayInputStream(object.readAllBytes());
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    @Override
    public InputStream downloads(Long bindId) {
        List<StoFile> files = getFiles(bindId);
        if (files.isEmpty()) {
            return null;
        }
        final File tempFile = FileUtil.createTempFile("TMP", ".zip", false);
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
            log.error("", e);
        }
        try {
            return new FileInputStream(tempFile);
        } catch (FileNotFoundException e) {
            log.error("", e);
        }
        return null;
    }

    @Override
    public StoFile putFile(StoFile file, InputStream is) {
        createFilePath(file);
        createStorageType(file);
        createFileSize(file, is);
        try {
            minioClient().putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(file.getFilePath())
                    .stream(is, is.available(), -1)
                    .contentType(file.getContentType())
                    .build());
            this.save(file);
        } catch (Exception e) {
            log.error("", e);
        }
        file.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat(String.valueOf(file.getId())));
        return file;
    }

    @Override
    public boolean delFile(Long id) {
        return this.lambdaUpdate()
                .eq(StoFile::getId, id)
                .set(StoFile::getDelTag, 1)
                .update();
    }

    @Override
    public boolean delFiles(Long bindId) {
        return this.lambdaUpdate()
                .eq(StoFile::getBindId, bindId)
                .set(StoFile::getDelTag, 1)
                .update();
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
     * @param is  InputStream
     */
    private void createFileSize(StoFile file, InputStream is) {
        if (Objects.isNull(file.getFileSize())) {
            try {
                file.setFileSize((long) is.available());
            } catch (IOException e) {
                log.error("", e);
                file.setFileSize(0L);
            }
        }
    }
}
