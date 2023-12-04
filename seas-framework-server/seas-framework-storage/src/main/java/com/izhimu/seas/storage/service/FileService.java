package com.izhimu.seas.storage.service;

import cn.hutool.core.util.IdUtil;
import com.izhimu.seas.storage.entity.FileInfo;
import com.izhimu.seas.storage.entity.StoFile;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static com.izhimu.seas.storage.service.impl.StoFileServiceImpl.BASE_URL;

/**
 * 文件服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface FileService {

    /**
     * 以流的形式下载文件
     *
     * @param id 文件ID
     * @param os 输出流
     * @throws FileNotFoundException 文件不存在异常
     */
    void downloadAsStream(Long id, OutputStream os) throws FileNotFoundException;

    /**
     * 作为HTTP下载
     *
     * @param id       文件ID
     * @param response HTTP响应对象
     * @throws FileNotFoundException 文件不存在异常
     */
    void downloadAsHttp(Long id, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 以流的形式下载压缩文件
     *
     * @param bindId 绑定ID
     * @param os     输出流
     * @throws FileNotFoundException 文件不存在异常
     */
    void downloadZipAsStream(Long bindId, OutputStream os) throws FileNotFoundException;

    /**
     * 作为HTTP下载Zip文件
     *
     * @param bindId   绑定ID
     * @param response HTTP响应对象
     * @throws FileNotFoundException 文件不存在异常
     */
    void downloadZipAsHttp(Long bindId, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 以流的形式预览文件
     *
     * @param id   文件ID
     * @param type 预览类型
     * @param os   输出流
     * @throws FileNotFoundException 文件不存在异常
     */
    void previewAsStream(Long id, String type, OutputStream os, Map<String, Object> param) throws FileNotFoundException;

    /**
     * 作为HTTP预览
     *
     * @param id       文件ID
     * @param type     预览类型
     * @param response HTTP响应对象
     * @throws FileNotFoundException 文件不存在异常
     */
    void previewAsHttp(Long id, String type, HttpServletResponse response, Map<String, Object> param) throws FileNotFoundException;

    /**
     * 上传文件
     *
     * @param file 要上传的文件
     * @param is   文件输入流
     * @return 上传后的文件对象
     */
    StoFile upload(StoFile file, InputStream is);

    /**
     * 获取文件预览信息
     *
     * @param id 文件ID
     * @return 文件信息对象
     * @throws FileNotFoundException 文件不存在异常
     */
    FileInfo previewInfo(Long id) throws FileNotFoundException;

    /**
     * 计算文件大小
     *
     * @param file SysFile
     * @param is   InputStream
     */
    default void calculateFileSize(StoFile file, InputStream is) throws IOException {
        if (Objects.isNull(file.getFileSize())) {
            file.setFileSize((long) is.available());
        }
    }

    /**
     * 生成文件路径
     *
     * @param file SysFile
     */
    default void createFilePath(StoFile file) {
        if (Objects.isNull(file.getId())) {
            file.setId(IdUtil.getSnowflakeNextId());
        }
        if (Objects.isNull(file.getFilePath())) {
            LocalDateTime now = LocalDateTime.now();
            String str = now.getYear() +
                    File.separator +
                    now.getMonthValue() +
                    File.separator +
                    now.getDayOfMonth() +
                    File.separator +
                    file.getId();
            file.setFilePath(str);
        }
        if (Objects.isNull(file.getFileUrl())) {
            file.setFileUrl(BASE_URL.concat(String.valueOf(file.getId())));
        }
    }
}
