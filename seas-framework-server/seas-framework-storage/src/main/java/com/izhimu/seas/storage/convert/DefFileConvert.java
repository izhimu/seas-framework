package com.izhimu.seas.storage.convert;

import cn.hutool.core.io.IoUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * 默认文件转换
 *
 * @author haoran
 * @version v1.0
 */
public class DefFileConvert extends SimpleFileConvert {
    @Override
    public void convert(InputStream is, OutputStream os, Map<String, Object> param) {
        IoUtil.copy(is, os);
    }
}
