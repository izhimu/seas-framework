package com.izhimu.seas.storage.service;

import com.izhimu.seas.data.service.IBaseService;
import com.izhimu.seas.storage.dto.SysFileDTO;
import com.izhimu.seas.storage.entity.SysFile;
import com.izhimu.seas.storage.vo.SysFileVO;

import java.io.InputStream;
import java.util.List;

/**
 * 文件服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface SysFileService extends IBaseService<SysFile> {

    /**
     * 获取文件
     *
     * @param id id
     * @return SysFileVO
     */
    SysFileVO getFile(Long id);

    /**
     * 获取文件
     *
     * @param bindId 绑定ID
     * @return List<SysFileVO>
     */
    List<SysFileVO> getFiles(Long bindId);

    /**
     * 获取文件并压缩
     *
     * @param bindId 绑定ID
     * @return List<SysFileVO>
     */
    SysFileVO getFilesToCompression(Long bindId);

    /**
     * 下载文件
     *
     * @param id id
     * @return InputStream
     */
    InputStream download(Long id);

    /**
     * 下载文件
     *
     * @param bindId 绑定ID
     * @return InputStream
     */
    InputStream downloads(Long bindId);

    /**
     * 保存文件
     *
     * @param dto SysFileDTO
     * @param is  InputStream
     * @return SysFileDTO
     */
    SysFileVO putFile(SysFileDTO dto, InputStream is);

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
