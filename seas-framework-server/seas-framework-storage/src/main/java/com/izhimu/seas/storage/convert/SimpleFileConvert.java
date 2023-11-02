package com.izhimu.seas.storage.convert;

import com.izhimu.seas.storage.entity.FileInfo;

import java.io.InputStream;

/**
 * 简单文件转换
 *
 * @author haoran
 * @version v1.0
 */
public abstract class SimpleFileConvert implements IFileConvert {

    @Override
    public FileInfo getFileInfo(InputStream is) {
        return null;
    }
}
