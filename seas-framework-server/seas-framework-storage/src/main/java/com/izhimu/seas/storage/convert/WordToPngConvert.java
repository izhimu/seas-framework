package com.izhimu.seas.storage.convert;

import com.aspose.words.*;
import com.izhimu.seas.core.log.LogWrapper;
import com.izhimu.seas.storage.entity.FileInfo;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Word转图片
 *
 * @author haoran
 * @version v1.0
 */
public class WordToPngConvert implements IFileConvert {

    private static final LogWrapper log = LogWrapper.build("FileConvert");

    @Override
    public void convert(InputStream is, OutputStream os, Map<String, Object> param) {
        try {
            Document doc = new Document(is);
            ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
            options.setPrettyFormat(true);
            options.setUseAntiAliasing(true);
            options.setUseHighQualityRendering(true);
            options.setPageSet(new PageSet((Integer) param.getOrDefault("page", 0)));
            doc.save(os, options);
        } catch (Exception e) {
            log.error("Word to png error", e);
        }
    }

    @Override
    public FileInfo getFileInfo(InputStream is) {
        try {
            Document doc = new Document(is);
            PageSetup pageSetup = doc.getFirstSection().getPageSetup();
            FileInfo fileInfo = new FileInfo();
            fileInfo.setPageCount(doc.getPageCount());
            fileInfo.setWidth(pageSetup.getPageWidth());
            fileInfo.setHeight(pageSetup.getPageHeight());
            return fileInfo;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }
}
