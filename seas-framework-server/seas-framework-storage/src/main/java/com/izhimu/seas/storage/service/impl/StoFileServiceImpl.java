package com.izhimu.seas.storage.service.impl;

import com.izhimu.seas.data.service.impl.BaseServiceImpl;
import com.izhimu.seas.storage.entity.StoFile;
import com.izhimu.seas.storage.mapper.StoFileMapper;
import com.izhimu.seas.storage.service.StoFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public static final String BASE_URL = "/sto/file/";

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
        file.setFileUrl(BASE_URL.concat(String.valueOf(file.getId())));
        return file;
    }

    @Override
    public List<StoFile> getFiles(Long bindId) {
        List<StoFile> files = this.lambdaQuery()
                .eq(StoFile::getDelTag, 0)
                .eq(StoFile::getBindId, bindId)
                .list();
        files.forEach(file -> file.setFileUrl(BASE_URL.concat(String.valueOf(file.getId()))));
        return files;
    }

    @Override
    public StoFile getFilesToCompression(Long bindId) {
        StoFile file = new StoFile();
        file.setId(bindId);
        file.setFileUrl(BASE_URL.concat("bind/").concat(String.valueOf(file.getId())));
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
}
