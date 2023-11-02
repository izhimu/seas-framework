package com.izhimu.seas.storage.convert;

import com.izhimu.seas.storage.entity.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * 文件转换接口
 *
 * @author haoran
 * @version v1.0
 */
public interface IFileConvert {

    /**
     * 转换
     *
     * @param is    InputStream
     * @param os    OutputStream
     * @param param 转换参数
     */
    void convert(InputStream is, OutputStream os, Map<String, Object> param);

    /**
     * 获取文件信息
     *
     * @param is InputStream
     * @return FileInfo
     */
    FileInfo getFileInfo(InputStream is);
}
