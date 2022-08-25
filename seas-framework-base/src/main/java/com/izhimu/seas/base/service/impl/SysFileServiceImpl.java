package com.izhimu.seas.base.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.cglib.CglibUtil;
import cn.hutool.extra.compress.CompressUtil;
import cn.hutool.extra.compress.archiver.Archiver;
import com.izhimu.seas.base.config.MinioConfig;
import com.izhimu.seas.base.dto.SysFileDTO;
import com.izhimu.seas.base.entity.SysFile;
import com.izhimu.seas.base.mapper.SysFileMapper;
import com.izhimu.seas.base.service.SysFileService;
import com.izhimu.seas.base.vo.SysFileVO;
import com.izhimu.seas.mybatis.service.impl.BaseServiceImpl;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class SysFileServiceImpl extends BaseServiceImpl<SysFileMapper, SysFile> implements SysFileService {

    private static final String BASE_URL = "/sys/file/";

    private MinioClient minioClient;

    @Resource
    private MinioConfig minioConfig;

    @Override
    public SysFileVO getFile(Long id) {
        Optional<SysFile> sysFile = this.lambdaQuery()
                .eq(SysFile::getDelTag, 0)
                .eq(SysFile::getId, id)
                .oneOpt();
        if (sysFile.isEmpty()) {
            return null;
        }
        SysFileVO vo = CglibUtil.copy(sysFile.get(), SysFileVO.class);
        vo.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat(String.valueOf(vo.getId())));
        return vo;
    }

    @Override
    public List<SysFileVO> getFiles(Long bindId) {
        List<SysFile> list = this.lambdaQuery()
                .eq(SysFile::getDelTag, 0)
                .eq(SysFile::getBindId, bindId)
                .list();
        List<SysFileVO> vos = CglibUtil.copyList(list, SysFileVO::new);
        vos.forEach(vo -> vo.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat(String.valueOf(vo.getId()))));
        return vos;
    }

    @Override
    public SysFileVO getFilesToCompression(Long bindId) {
        SysFileVO vo = new SysFileVO();
        vo.setId(bindId);
        vo.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat("bind/").concat(String.valueOf(vo.getId())));
        return vo;
    }

    @Override
    public InputStream download(Long id) {
        SysFileVO file = getFile(id);
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
        List<SysFileVO> files = getFiles(bindId);
        if (files.isEmpty()) {
            return null;
        }
        final File tempFile = FileUtil.createTempFile("TMP", ".zip", false);
        try (Archiver archiver = CompressUtil.createArchiver(CharsetUtil.CHARSET_UTF_8, ArchiveStreamFactory.ZIP, tempFile)) {
            for (SysFileVO file : files) {
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
    public SysFileVO putFile(SysFileDTO dto, InputStream is) {
        createFilePath(dto);
        createStorageType(dto);
        createFileSize(dto, is);
        try {
            minioClient().putObject(PutObjectArgs.builder()
                    .bucket(minioConfig.getBucket())
                    .object(dto.getFilePath())
                    .stream(is, is.available(), -1)
                    .contentType(dto.getContentType())
                    .build());
            this.save(CglibUtil.copy(dto, SysFile.class));
        } catch (Exception e) {
            log.error("", e);
        }
        SysFileVO vo = CglibUtil.copy(dto, SysFileVO.class);
        vo.setFileUrl(minioConfig.getProxyHost().concat(BASE_URL).concat(String.valueOf(vo.getId())));
        return vo;
    }

    @Override
    public boolean delFile(Long id) {
        this.lambdaUpdate()
                .eq(SysFile::getId, id)
                .set(SysFile::getDelTag, 1)
                .update();
        return true;
    }

    @Override
    public boolean delFiles(Long bindId) {
        this.lambdaUpdate()
                .eq(SysFile::getBindId, bindId)
                .set(SysFile::getDelTag, 1)
                .update();
        return true;
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
     * @param dto SysFileDTO
     */
    private void createId(SysFileDTO dto) {
        if (Objects.isNull(dto.getId())) {
            dto.setId(IdUtil.getSnowflakeNextId());
        }
    }

    /**
     * 生成文件路径
     *
     * @param dto SysFileDTO
     */
    private void createFilePath(SysFileDTO dto) {
        createId(dto);
        if (Objects.isNull(dto.getFilePath())) {
            LocalDateTime now = LocalDateTime.now();
            String str = now.getYear() +
                    "/" +
                    now.getMonthValue() +
                    "/" +
                    now.getDayOfMonth() +
                    "/" +
                    dto.getId();
            dto.setFilePath(str);
        }
    }

    /**
     * 生成存储类型
     *
     * @param dto SysFileDTO
     */
    private void createStorageType(SysFileDTO dto) {
        if (Objects.isNull(dto.getStorageType())) {
            String str = "MINIO:" + minioConfig.getBucket();
            dto.setStorageType(str);
        }
    }

    /**
     * 生成文件大小
     *
     * @param dto SysFileDTO
     * @param is  InputStream
     */
    private void createFileSize(SysFileDTO dto, InputStream is) {
        if (Objects.isNull(dto.getFileSize())) {
            try {
                dto.setFileSize((long) is.available());
            } catch (IOException e) {
                log.error("", e);
                dto.setFileSize(0L);
            }
        }
    }
}
