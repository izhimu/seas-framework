package com.izhimu.seas.storage.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.storage.entity.StoFile;

import java.util.List;

/**
 * 文件服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface StoFileService extends IBaseService<StoFile> {

    /**
     * 获取文件
     *
     * @param id id
     * @return SysFile
     */
    StoFile getFile(Long id);

    /**
     * 获取文件
     *
     * @param bindId 绑定ID
     * @return List<SysFile>
     */
    List<StoFile> getFiles(Long bindId);

    /**
     * 获取文件并压缩
     *
     * @param bindId 绑定ID
     * @return List<SysFile>
     */
    StoFile getFilesToCompression(Long bindId);

    /**
     * 删除文件
     *
     * @param id id
     * @return boolean
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean delFile(Long id);

    /**
     * 删除文件
     *
     * @param bindId 绑定ID
     * @return boolean
     */
    @SuppressWarnings("UnusedReturnValue")
    boolean delFiles(Long bindId);

}
