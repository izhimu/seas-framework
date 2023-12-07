package com.izhimu.seas.storage.constant;

import com.izhimu.seas.storage.convert.IFileConvert;
import com.izhimu.seas.storage.convert.WordToPdfConvert;
import com.izhimu.seas.storage.convert.WordToPngConvert;

import java.util.Map;

/**
 * 文件预览相关常量
 *
 * @author haoran
 * @version v1.0
 */
public class PreviewConst {

    private PreviewConst() {
    }

    public static final Map<String, Class<? extends IFileConvert>> PDF_CONVERT_MAP = Map.of(
            "application/msword", WordToPdfConvert.class,
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", WordToPdfConvert.class
    );

    public static final Map<String, Class<? extends IFileConvert>> PNG_CONVERT_MAP = Map.of(
            "application/msword", WordToPngConvert.class,
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", WordToPngConvert.class
    );
}
