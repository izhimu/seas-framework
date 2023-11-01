package com.izhimu.seas.storage.service;

import com.izhimu.seas.storage.entity.StoFile;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface FileServerService {

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
     * @param id 文件ID
     * @param response HTTP响应对象
     * @throws FileNotFoundException 文件不存在异常
     */
    void downloadAsHttp(Long id, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 以流的形式下载压缩文件
     *
     * @param bindId 绑定ID
     * @param os 输出流
     * @throws FileNotFoundException 文件不存在异常
     */
    void downloadZipAsStream(Long bindId, OutputStream os) throws FileNotFoundException;

    /**
     * 作为HTTP下载Zip文件
     *
     * @param bindId 绑定ID
     * @param response HTTP响应对象
     * @throws FileNotFoundException 文件不存在异常
     */
    void downloadZipAsHttp(Long bindId, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 以流的形式预览文件
     *
     * @param id 文件ID
     * @param os 输出流
     * @throws FileNotFoundException 文件不存在异常
     */
    void previewAsStream(Long id, OutputStream os) throws FileNotFoundException;

    /**
     * 作为HTTP预览
     *
     * @param id 文件ID
     * @param response HTTP响应对象
     * @throws FileNotFoundException 文件不存在异常
     */
    void previewAsHttp(Long id, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 上传文件
     *
     * @param file 要上传的文件
     * @param is   文件输入流
     * @return 上传后的文件对象
     */
    StoFile upload(StoFile file, InputStream is);
}
