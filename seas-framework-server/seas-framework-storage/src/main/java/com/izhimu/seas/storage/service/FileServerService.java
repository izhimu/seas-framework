package com.izhimu.seas.storage.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.OutputStream;

/**
 * 文件服务接口
 *
 * @author haoran
 * @version v1.0
 */
public interface FileServerService {

    /**
     * 下载文件
     *
     * @param id id
     */
    void downloadAsStream(Long id, OutputStream os) throws FileNotFoundException;

    /**
     * 下载文件
     *
     * @param id id
     */
    void downloadAsHttp(Long id, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 下载文件
     *
     * @param bindId 绑定ID
     */
    void downloadZipAsStream(Long bindId, OutputStream os) throws FileNotFoundException;

    /**
     * 下载文件
     *
     * @param bindId 绑定ID
     */
    void downloadZipAsHttp(Long bindId, HttpServletResponse response) throws FileNotFoundException;
}
